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
- Bibliotecas: jQuery (JavaScript), jQ-Router (JavaScript) e Purecss (CSS)
- Requisições a API da aplicacao backend por meio de Ajax
- Deixei o projeto do frontend dentro do projeto do backend apenas para não ter que fazer 2 repositórios, mas são aplicações diferentes.
- Aplicação roda pelo protocolo HTTPS, e faz redirecionamento de HTTP para HTTPS
- Os arquivos index.php e composer.json na raiz do projeto foram adicionados para a aplicação rodar no Heroku (pois este servidor usa PHP e Bowser), mas é possível rodar a aplicação normalmente em um servidor http simples (como o embutido no Python) sem precisar de um servidor PHP.
Fontes: https://gist.github.com/wh1tney/2ad13aa5fbdd83f6a489, http://blog.teamtreehouse.com/deploy-static-site-heroku, https://devcenter.heroku.com/articles/getting-started-with-php#deploy-the-app

## Como rodar:

- Importe para o MySQL o script em supero-desafio\database\schema.sql 
- No arquivo application.properties coloque a senha do seu banco (propriedade spring.datasource.password)
- Gere o .jar do projeto (estando dentro da pasta supero-desafio) com o comando: mvn clean install package -DskipTests
- Rode o .jar do projeto localizado na pasta supero-desafio\target. A aplicação backend por padrão rodará (caso seja local) em: http://localhost:8080
- Para o frontend utilize um servidor Web para levantar a aplicação. Localmente utilizei o servidor Web embutido no Python (caso o Python esteja instalado e configurado em seu PC/notebook). Exemplo: entre na pasta supero-desafio-frontend e no terminal/console execute o comando: python -m http.server
- Caso tenha iniciado um servidor Web local com Python a aplicação frontend estará rodando no endereço padrão http://localhost:8000
- Modifique a url da API (servidor backend) em supero-desafio-frontend\application.json