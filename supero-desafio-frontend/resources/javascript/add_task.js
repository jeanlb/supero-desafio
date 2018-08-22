$(document).ready(function() {

	// requisicao ao servidor para adicionar task
	$("#form_add_task").submit(function() {

		var titulo = $("#titulo").val();
		var descricao = $("#descricao").val();

		$.ajax({
	        type: 'POST',
	        url: apiUrl + "/task/inserir",
	        data: {'titulo': titulo, 'descricao': descricao},
	        success: function(response) {
	        	location.href = "#/";
	        	$('#message').html('Task ' + titulo + ' adicionada com sucesso').css('color', 'blue');
	        }
	    });
	    return false;
	});  

});