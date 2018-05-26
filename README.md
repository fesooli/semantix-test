# semantix-test

####Qual o objetivo do comando cache em Spark?
R: O comando cache carrega as informações do RDD (Resilient Distributed Dataset) em memória.

####O mesmo código implementado em Spark é normalmente mais rápido que a implementação equivalente em MapReduce. Por quê?
R: Porque o Mapeduce trabalha com escrita e leitura em disco, em quanto que o spark trabalha com a estrutura de dados RDD que utiliza a memória para processamento.

####Qual é a função do SparkContext?
R: SparkContext tem a função de carregar as configurações do SparkConf para o driver Spark e gerenciar os recursos disponíveis do cluster.

####Explique com suas palavras o que é Resilient Distributed Datasets (RDD).
R: RDD é um conjunto de dados (dataset) em memória que pode ser particionado em muitas máquinas que fazem parte do cluster.

####GroupByKey é menos eficiente que reduceByKey em grandes dataset. Por quê?
R: Porque o groupByKey faz diversas operações de transferências de dados a mais que o reduceByKey não faz. Isso ocorre porque o reduceByKey faz as combinações dos dados em cada partição antes de embaralhar os dados, ao contrário do groupByKey.

####Explique o que o código Scala abaixo faz.
```
val textFile = sc . textFile ( "hdfs://..." )
val counts = textFile . flatMap ( line => line . split ( " " ))
. map ( word => ( word , 1 ))
. reduceByKey ( _ + _ )
counts . saveAsTextFile ( "hdfs://..." )
```
R: O código acima busca um conjunto de dados no HDFS (diretório de arquivos do hadoop), depois ele divide o conjunto de dados em uma lista a partir dos espaços em branco. Utilizando as transformações do flatMap, map e reduceByKey, ele faz a contagem de palavras encontradas no conjunto de dados do HDFS. Por fim, ele salva o resultado no HDFS.

####Responda as seguintes questões devem ser desenvolvidas em Spark utilizando a sua linguagem de preferência.

####1 - Número de hosts únicos.
R: Quantidade de Hosts Unicos: 137978

####2 - O total de erros 404.
R: Quantidade de Erros 404: 20901

####3 - Os 5 URLs que mais causaram erro 404.
R: ?

####4 - Quantidade de erros 404 por dia.
R: ?

####5 - O total de bytes retornados.
R: Quantidade total de bytes: 1099805475
