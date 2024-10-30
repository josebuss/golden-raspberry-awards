# Inicialização sistema
Para inicializar o sistema com dados é necessário adicionar um ou mais arquivos CSV no diretório "resources/carga-inicial"; 
Por padrão o arquivo fornecido já foi adicionado no diretório.

# Sugestão de execução do projeto
É possível executar a aplicação local (via run da IDE) ou via maven:
 - mvn package
 - java -jar target/golden.raspberry.awards-0.0.1-SNAPSHOT.jar

# Testes
Para a cobertura dos testes de integração foi pensado:
 - Um cenário que visa cobrir a consulta (get) com os dados já inicializados pela carga inicial do sistema;
 - Um cenário que visa cobrir o endpoint da inclusão (post) que adiciona uma linha nova aos dados já adicionados ao sistema;
 - Cenários que validam integridade dos arquivos importados (seja via post, seja via inicialização); 
 
Obs.: Os cenários que envolvem a validação da carga inicial e a inserção de uma nova linha dependem que o sistema seja iniciado com o arquivo padrão, porém a busca (GET - api/produtores-premiados) funciona com qualquer arquivo válido. 
  
Além dos testes integrados, foram criados testes unitários para garantir a integridade do código. 

Abaixo estão disponibilizados os dados necessários para executar os testes automatizados e manuais.

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
