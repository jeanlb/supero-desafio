$(document).ready(function() {

	// pegar valor do parametro id na url que estah apos a ultima '/'
	var idEncriptado = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);

	// pegar a task pelo id e renderizar dados na view
	$.ajax({
        type: 'GET',
        url: apiUrl + "/task/" + idEncriptado,
        success: function(task) {
        	$("#titulo").val(task.titulo);
        	$("#descricao").val(task.descricao);
        	$("#status_concluido").prop("checked", task.statusConcluido);
        }
    });

});