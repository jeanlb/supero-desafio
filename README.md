# supero-desafio
Projeto de Tasklist utilizando Java/MySQL.

## Características

Backend (supero-desafio):
- Java
- Frameworks: Spring Boot, JPA e Hibernate, Maven
- Padrão MVC com DTO (incluindo conversão de entity para dto e dto para entity), Service e DAO
- Restful
- MySQL

Frontend (supero-desafio-frontend):
- HTML5, CSS, JavaScript
- Bibliotecas: jQuery (JavaScript) e Purecss (CSS)
- Requisições a API da aplicacao backend por meio de Ajax
- Esta primeira versão não está configurada com NodeJS como servidor Web (que pretendo utilizar, mas esta primeira versão do frontend fiz um pouco corrida)
- Deixei o projeto do frontend dentro do projeto do backend apenas para não ter que fazer 2 repositórios, mas são aplicações diferentes. 

## Como rodar:

- Importe para o MySQL o script em supero-desafio\database\schema.sql 
- No arquivo application.properties coloque a senha do seu banco (spring.datasource.password)
- Gere o .jar do projeto (estando dentro da pasta supero-desafio) com o comando: mvn clean install package -DskipTests
- Rode o .jar do projeto
- Para o frontend (supero-desafio-frontend) abra no browser o arquivo index.html