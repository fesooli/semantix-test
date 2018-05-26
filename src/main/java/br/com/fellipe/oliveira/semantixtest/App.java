package br.com.fellipe.oliveira.semantixtest;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.regex.RegexLineRecordReader;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.misc.StringToWritablesFunction;

import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("BusProcessor");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        JavaRDD<String> logsAug = ctx.textFile("NASA_access_log_Aug95");
        JavaRDD<String> logsJul = ctx.textFile("NASA_access_log_Jul95");
        JavaRDD<String> logs = logsJul.union(logsAug);

        String regex = "(\\S+) - - \\[(\\S+ -\\d{4})\\] \"(.+)\" (\\d+) (\\d+|-)";
        RecordReader rr = new RegexLineRecordReader(regex,0);
        JavaRDD<List<Writable>> logsParsed = logs.map(new StringToWritablesFunction(rr));

        Map<Integer, List<String>> map = transform(logsParsed
                .map(p -> p.stream()
                        .map(Writable::toString)
                        .collect(Collectors.toList()))
                .flatMap(p -> p)
                .collect());

        Long uniqueHosts = map.get(0).stream().distinct().count();
        System.out.println("Quantidade de Hosts Unicos: " + uniqueHosts);

        Long numberOf404Erros = map.get(3).stream().filter(m -> m.equals("404")).count();
        System.out.println("Quantidade de Erros 404: " + numberOf404Erros);

        Integer totalBytes =  map.get(4)
                .stream()
                .mapToInt(i -> {
                    try {
                        return Integer.valueOf(i).intValue();
                    } catch (Exception e ) {
                        return 0;
                    }
                })
                .sum();
        System.out.println("Quantidade total de bytes: " + totalBytes);

        ctx.close();
    }

    private static Map<Integer, List<String>> transform(List<String> list) {
        int quantidade = 5;
        Map<Integer, List<String>> r = new HashMap<>();
        int j = 0;
        for(int i = 0; i < list.size(); i++){
            try {
                if (j == quantidade) {
                    j = 0;
                }
                List<String> aux = r.get(j);
                if (aux == null) {
                    aux = new ArrayList<>();
                }
                aux.add(list.get(i).toString());
                r.put(j, aux);
                j++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return r;
    }
}
