# Sugestão de execução do projeto
É possível executar a aplicação local (via run da IDE) ou via maven:
 - mvn package
 - java -jar target/golden.raspberry.awards-0.0.1-SNAPSHOT.jar


Abaixo estão disponibilizados os dados necessários para executar os testes

Comando | Descrição
--------|-----------
mvn -Dtest=ProdutoresPremiadosControllerTest test | Executa somente o teste integração
mvn test | Executa todos os testes


# Acesso console banco H2
http://localhost:8080/console/

Variável | Valor
 ------- | -----
JDBC URL | jdbc:h2:mem:test
username | sa
password |


# API
localhost:8080/api/produtores-premiados
|Protocolo|Ação|Exemplo|
|-|-|-|
|GET|Lista os Produtores com menor e mais intervalo de premios|`curl --location --request GET 'localhost:8080/api/produtores-premiados' \ --form 'file=@"/C:/git/movielist.csv"'`
|POST|Inseri os filmes candidatos e vencedores do prêmio|`curl --location 'localhost:8080/api/produtores-premiados' \ --form 'file=@"/C:/git/movielist.csv"'`

