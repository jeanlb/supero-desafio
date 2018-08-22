# supero-desafio-frontend
Projeto de Tasklist. Aplicação Frontend consumidora da aplicação servidora (Backend) supero-desafio.

## Características

Frontend (supero-desafio-frontend):
- HTML5, CSS, JavaScript
- Bibliotecas: jQuery (JavaScript), jQ-Router (JavaScript) e Purecss (CSS)
- Requisições a API da aplicacao backend por meio de Ajax
- Os arquivos index.php e composer.json na raiz do projeto foram adicionados para a aplicação rodar no Heroku (pois este servidor usa PHP e Bowser), mas é possível rodar a aplicação normalmente em um servidor http simples (como o embutido no Python) sem precisar de um servidor PHP.
Fontes: https://gist.github.com/wh1tney/2ad13aa5fbdd83f6a489, http://blog.teamtreehouse.com/deploy-static-site-heroku, https://devcenter.heroku.com/articles/getting-started-with-php#deploy-the-app

## Como rodar:

- Utilize um servidor Web para levantar a aplicação. Localmente utilizei o servidor Web embutido no Python (caso o Python esteja instalado e configurado em seu PC/notebook). Exemplo: entre na pasta supero-desafio-frontend e no terminal/console execute o comando: python -m http.server
- Caso tenha iniciado um servidor Web local com Python a aplicação frontend estará rodando no endereço padrão http://localhost:8000
- Modifique a url da API (servidor backend) em supero-desafio-frontend\application.json