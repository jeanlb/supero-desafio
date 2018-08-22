// variavel global para armazenar a url da api
var apiUrl;

// pegar url da api no arquivo de configuracoes da aplicacao
function getApiUrl() {

    $.ajax({
        url: "../../application.json",
        type: "GET",
        dataType: "json",
        cache: false,
        async:false,
        success: function(response) {
            apiUrl = response.apiUrl;
        }
    });
}

$(document).ready(function() {

    /* pegar url da api no arquivo de configuracoes da aplicacao ao inicializar 
       aplicacao (ao renderizar pela primeira vez o arquivo index.html) */
    getApiUrl();

});