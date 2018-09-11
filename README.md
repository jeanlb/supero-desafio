# supero-desafio
Projeto de Tasklist utilizando Java/MySQL.

## Características

Backend (supero-desafio):
- Java
- Frameworks: Spring Boot, JPA e Hibernate, Maven
- Padrão MVC com DTO (incluindo conversão de entity para dto e dto para entity), Service e DAO
- Restful
- MySQL
- Memcached (Java client: spymemcached)

Frontend (supero-desafio-frontend):
- HTML5, CSS, JavaScript
- Bibliotecas: jQuery (JavaScript), jQ-Router (JavaScript) e Purecss (CSS)
- Requisições a API da aplicacao backend por meio de Ajax
- Deixei o projeto do frontend dentro do projeto do backend apenas para não ter que fazer 2 repositórios, mas são aplicações diferentes.
- Os arquivos index.php e composer.json na raiz do projeto foram adicionados para a aplicação rodar no Heroku (pois este servidor usa PHP e Bowser), mas é possível rodar a aplicação normalmente em um servidor http simples (como o embutido no Python) sem precisar de um servidor PHP.
Fontes: https://gist.github.com/wh1tney/2ad13aa5fbdd83f6a489, http://blog.teamtreehouse.com/deploy-static-site-heroku, https://devcenter.heroku.com/articles/getting-started-with-php#deploy-the-app

## Como rodar:

- Importe para o MySQL o script em supero-desafio\database\schema.sql
- Servidor Memcached (cache) instalado
- No arquivo application.properties coloque a senha do seu banco (propriedade spring.datasource.password). Neste arquivo também estão as configurações do servidor de cache (memcached.host) e porta (memcached.port)
- Gere o .jar do projeto (estando dentro da pasta supero-desafio) com o comando: mvn clean install package -DskipTests
- Rode o .jar do projeto localizado na pasta supero-desafio\target. A aplicação backend por padrão rodará (caso seja local) em: http://localhost:8080
- Para o frontend utilize um servidor Web para levantar a aplicação. Localmente utilizei o servidor Web embutido no Python (caso o Python esteja instalado e configurado em seu PC/notebook). Exemplo: entre na pasta supero-desafio-frontend e no terminal/console execute o comando: python -m http.server
- Caso tenha iniciado um servidor Web local com Python a aplicação frontend estará rodando no endereço padrão http://localhost:8000
- Modifique a url da API (servidor backend) em supero-desafio-frontend\application.json

## Configurar Nginx para adicionar segurança SSL/HTTPS com Let's Encrypt no Ubuntu 16.04:

- Web Sources: 
https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-16-04
https://calum.github.io/2018/01/20/Make-your-web-app-use-HTTPS-in-30-minutes.html
- Para testes, criar dominio gratuito no dot.tk (freenom). Depois nas configuracoes do dominio, ir em Services > My Domains> Manage Domain > Manage Freenom DNS > Adicionar/editar os Records em branco e com WWW com o campo Target sendo o IP do Servidor (Com o LetsEncrypt nao funciona para ip local, tende de ser em um servidor publico, com os criados na AWS-EC2)  