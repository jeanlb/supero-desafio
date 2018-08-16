$(document).ready(function() {

	    $.ajax({
	    	url: "http://localhost:8080/task",
	    	type: "GET",
	    	dataType: "json",
	    	cache: false,
	    	success: function(response) {
	        	renderizarTabela(response);
	    	}
		});

		function renderizarTabela(data) {
			for (var i = 0; i < data.length; i++) {
		        renderizarLinha(data[i]);
		    }
		}

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

});

function concluirTask(id) {
	$.ajax({
    	url: "http://localhost:8080/task/atualizar/status/" + id + "/" + true,
    	type: "PUT",
    	success: function(response) {
    		alert(response);
    		location.reload();
    	}
	});
}

function reativarTask(id) {
	$.ajax({
    	url: "http://localhost:8080/task/atualizar/status/" + id + "/" + false,
    	type: "PUT",
    	success: function(response) {
    		alert(response);
    		location.reload();
    	}
	});
}

function visualizarTask(id) {
	location.href = "view.html?id=" + id;
}

function editarTask(id) {
	location.href = "edit.html?id=" + id;
}

function deletarTask(id) {
	if (confirm('Tem certeza de que deseja deletar esta task?')) {
		$.ajax({
	    	url: "http://localhost:8080/task/" + id,
	    	type: "DELETE",
	    	success: function(response) {
	    		alert(response);
	    		location.reload();
	    	}
		});
	}
}