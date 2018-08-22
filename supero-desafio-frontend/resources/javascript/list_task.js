$(document).ready(function() {

	// renderizar na tabela as tasks atualizadas
    listarTasks();
});

// chamada ajax ao servidor para pegar a lista de tasks
function listarTasks() {

	$.ajax({
    	url: apiUrl + "/task",
    	type: "GET",
    	dataType: "json",
    	cache: false,
    	success: function(response) {
        	renderizarTabela(response);
    	}
	});
}

function renderizarTabela(data) {

	// limpa o conteudo atual da tabela (tbody)
	$('#task_table > tbody').empty();

	// renderiza na tabela os dados atualizados e cria as linhas da tabela
	for (var i = 0; i < data.length; i++) {
        renderizarLinha(data[i]);
    }
}

// criar/renderizar as linhas da tabela
function renderizarLinha(task) {

    var linha = $("<tr />")
    $("#task_table").append(linha);
    linha.append($("<td>" + task.titulo + "</td>"));
    linha.append($("<td>" + task.dataCriacaoFormatada + "</td>"));
    linha.append($("<td>" + task.dataModificacaoFormatada + "</td>"));
    linha.append($("<td>" + task.statusConcluidoFormatado + "</td>"));

    linha.append($("<td><button class='pure-button pure-button-primary' data-id='" + task.idEncriptado + "' onclick='concluirTask(this)' >Concluir</button>" + 
    	" <button class='pure-button' data-id='" + task.idEncriptado + "' onclick='reativarTask(this)' >Reativar</button>" + 
    	" <button class='pure-button pure-button-primary' data-id='" + task.idEncriptado + "' onclick='visualizarTask(this)' >Visualizar</button>" + 
    	" <button class='btn-editar pure-button' data-id='" + task.idEncriptado + "' onclick='editarTask(this)' >Editar</button>" + 
    	" <button class='btn-deletar pure-button' data-id='" + task.idEncriptado + "' onclick='deletarTask(this)' >Deletar</button></td>"));
}

// requisicao a view de visualizar task
function visualizarTask(btnVisualizar) {
    var idEncripted = btnVisualizar.getAttribute('data-id');
	location.href = "#/view/" + idEncripted;
}

// requisicao a view de editar task
function editarTask(btnEditar) {
    var idEncripted = btnEditar.getAttribute('data-id');
	location.href = "#/edit/" + idEncripted;
}

// requisicao ao servidor para concluir uma task pelo id
function concluirTask(btnConcluir) {

    var idEncripted = btnConcluir.getAttribute('data-id');

	$.ajax({
    	url: apiUrl + "/task/atualizar/status/" + idEncripted + "/" + true,
    	type: "PUT",
    	success: function(response) {
    		listarTasks();
    		$('#message').html(response).css('color', 'blue');
    	}
	});
}

// requisicao ao servidor para reativar uma task pelo id
function reativarTask(btnReativar) {

    var idEncripted = btnReativar.getAttribute('data-id');

	$.ajax({
    	url: apiUrl + "/task/atualizar/status/" + idEncripted + "/" + false,
    	type: "PUT",
    	success: function(response) {
    		listarTasks();
    		$('#message').html(response).css('color', 'red');
    	}
	});
}

// requisicao ao servidor para deletar uma task pelo id
function deletarTask(btnDeletar) {

    var idEncripted = btnDeletar.getAttribute('data-id');

	if (confirm('Tem certeza de que deseja deletar esta task?')) {
		$.ajax({
	    	url: apiUrl + "/task/" + idEncripted,
	    	type: "DELETE",
	    	success: function(response) {
	    		listarTasks();
	    		$('#message').html(response).css('color', 'red');
	    	}
		});
	}
}