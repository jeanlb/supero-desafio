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

    linha.append($("<td><button class='pure-button pure-button-primary' onclick='concluirTask(" + task.id + ")' >Concluir</button>" + 
    	" <button class='pure-button' onclick='reativarTask(" + task.id + ")' >Reativar</button>" + 
    	" <button class='pure-button pure-button-primary' onclick='visualizarTask(" + task.id + ")' >Visualizar</button>" + 
    	" <button class='btn-editar pure-button' onclick='editarTask(" + task.id + ")' >Editar</button>" + 
    	" <button class='btn-deletar pure-button' onclick='deletarTask(" + task.id + ")' >Deletar</button></td>"));
}

// requisicao a view de visualizar task
function visualizarTask(id) {
	location.href = "#/view/" + id;
}

// requisicao a view de editar task
function editarTask(id) {
	location.href = "#/edit/" + id;
}

// requisicao ao servidor para concluir uma task pelo id
function concluirTask(id) {
	$.ajax({
    	url: apiUrl + "/task/atualizar/status/" + id + "/" + true,
    	type: "PUT",
    	success: function(response) {
    		listarTasks();
    		$('#message').html(response).css('color', 'blue');
    	}
	});
}

// requisicao ao servidor para reativar uma task pelo id
function reativarTask(id) {
	$.ajax({
    	url: apiUrl + "/task/atualizar/status/" + id + "/" + false,
    	type: "PUT",
    	success: function(response) {
    		listarTasks();
    		$('#message').html(response).css('color', 'red');
    	}
	});
}

// requisicao ao servidor para deletar uma task pelo id
function deletarTask(id) {
	if (confirm('Tem certeza de que deseja deletar esta task?')) {
		$.ajax({
	    	url: apiUrl + "/task/" + id,
	    	type: "DELETE",
	    	success: function(response) {
	    		listarTasks();
	    		$('#message').html(response).css('color', 'red');
	    	}
		});
	}
}