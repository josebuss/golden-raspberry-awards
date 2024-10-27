# Concole Banco H2
http://localhost:8080/console/

Variável | Valor
 ------- | -----
JDBC URL | jdbc:h2:mem:test
username | sa
password |


#API
localhost:8080/api/produtores-premiados
|Protocolo|Ação|Exemplo|
|-|-|-|
|GET|Lista os Produtores com menor e mais intervalo de premios|`curl --location --request GET 'localhost:8080/api/produtores-premiados' \ --form 'file=@"/C:/git/movielist.csv"'`
|POST|Inseri os filmes candidatos e vencedores do prêmio|`curl --location 'localhost:8080/api/produtores-premiados' \ --form 'file=@"/C:/git/movielist.csv"'`