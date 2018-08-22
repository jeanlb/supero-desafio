$(document).ready(function() {

	// pegar valor do parametro id na url que estah apos a ultima '/'
	var taskId = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);

	// pegar a task pelo id e renderizar dados na view
	$.ajax({
        type: 'GET',
        url: apiUrl + "/task/" + taskId,
        success: function(task) {
        	$("#task_id").val(task.id);
        	$("#titulo").val(task.titulo);
        	$("#descricao").val(task.descricao);
        	$("#status_concluido").prop("checked", task.statusConcluido);
        }
    });

	// requisicao ao servidor para atualizar task
	$("#form_edit_task").submit(function() {

		var id = $("#task_id").val();
		var titulo = $("#titulo").val();
		var descricao = $("#descricao").val();
		var statusConcluido = ($("#status_concluido").prop("checked")) ? true : false;

		$.ajax({
	        type: 'PUT',
	        url: apiUrl + "/task/atualizar",
	        data: {'id': id, 'titulo': titulo, 'descricao': descricao, 'statusConcluido': statusConcluido},
	        success: function(response) {
	        	location.href = "#/";
	        	$('#message').html('Task ' + titulo + ' atualizada com sucesso').css('color', 'blue');
	        }
	    });
	    return false;
	});  

});