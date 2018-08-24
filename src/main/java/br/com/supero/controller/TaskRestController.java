package br.com.supero.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.supero.encrypt.CipherEncryptURLParameter;
import br.com.supero.model.dto.TaskDTO;
import br.com.supero.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskRestController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping()
	public List<TaskDTO> listar() {
		List<TaskDTO> tasks = taskService.listar();
		return tasks;
	}
	
	@GetMapping("/{id}")
	public TaskDTO getTaskPorId(@PathVariable(value = "id") String idEncrypted) {
		
		Long id = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncrypted.toString()));
		TaskDTO task = taskService.getTaskDTOPorId(id);
        return task;
    }

	@PostMapping("/inserir")
	public TaskDTO inserir(@Valid TaskDTO taskDTO) {
		TaskDTO taskInserida = taskService.inserir(taskDTO);
		
		return taskInserida;
	}
	
	@PutMapping("/atualizar")
	public TaskDTO atualizar(@Valid TaskDTO taskDTO) {
		TaskDTO taskAtualizada = taskService.atualizar(taskDTO);
		
		return taskAtualizada;
	}
	
	@PutMapping("/atualizar/status/{id}/{statusConcluido}")
	public ResponseEntity<?> atualizarStatus(@PathVariable(value = "id") String idEncrypted, 
			@PathVariable(value = "statusConcluido") Boolean statusConcluido) {
		
		Long id = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncrypted.toString()));
		
		taskService.atualizarStatus(id, statusConcluido);
		
		String mensagem = "Task concluida";
		if (!statusConcluido) mensagem = "Task reativada para ser concluida";
		
		return ResponseEntity.ok().body(mensagem);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable(value = "id") String idEncrypted) {
	    
		Long id = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncrypted.toString()));
		boolean foiDeletada = taskService.deletar(id);
		
		if (!foiDeletada)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Task nao foi deletada. Possivelmente este id nao existe");
			
	    return ResponseEntity.ok().body("Task deletada com sucesso");
	}

}