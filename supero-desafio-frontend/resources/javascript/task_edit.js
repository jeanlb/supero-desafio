$(document).ready(function() {

	var taskId = window.location.search.split('=')[1]; // pegar valor do parametro id na url

	$.ajax({
        type: 'GET',
        url: "http://localhost:8080/task/" + taskId,
        success: function(task) {
        	$("#task_id").val(task.id);
        	$("#titulo").val(task.titulo);
        	$("#descricao").val(task.descricao);
        	$("#status_concluido").prop("checked", task.statusConcluido);
        }
    });

	$("#form_edit_task").submit(function() {

		var id = $("#task_id").val();
		var titulo = $("#titulo").val();
		var descricao = $("#descricao").val();
		var statusConcluido = ($("#status_concluido").prop("checked")) ? true : false;

		$.ajax({
	        type: 'PUT',
	        url: "http://localhost:8080/task/atualizar",
	        data: {'id': id, 'titulo': titulo, 'descricao': descricao, 'statusConcluido': statusConcluido},
	        success: function(response) {
	        	alert('Task ' + titulo + ' atualizada com sucesso');
	        	location.href = "/";
	        }
	    });
	    return false;
	});  

});