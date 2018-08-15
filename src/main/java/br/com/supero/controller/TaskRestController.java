package br.com.supero.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import br.com.supero.model.dto.TaskDTO;
import br.com.supero.model.entity.Task;
import br.com.supero.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskRestController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ModelMapper modelMapper; // dependencia utilizada para converter entity para dto

	@GetMapping()
	public List<TaskDTO> listar() {
		List<Task> tasks = taskService.listar();
		List<TaskDTO> listTaskDTO = tasks.stream()
				.map(task -> convertToDto(task))
				.collect(Collectors.toList());

		return listTaskDTO;
	}

	@PostMapping("/inserir")
	public TaskDTO inserir(@Valid TaskDTO taskDTO) {
		Task task = convertToEntity(taskDTO);
		Task taskInserida = taskService.inserir(task);
		
		return convertToDto(taskInserida);
	}
	
	@PutMapping("/atualizar/status/{id}/{status}")
	public ResponseEntity<?> atualizarStatus(@PathVariable(value = "id") Long id, 
			@PathVariable(value = "status") Boolean status) {
		
		taskService.atualizarStatus(id, status);
		
		return ResponseEntity.ok().body("Task com status atualizado. id: " + id + " status: " + status);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable(value = "id") Long id) {
	    
		boolean foiDeletada = taskService.deletar(id);
		
		if (!foiDeletada)
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Task de id " + id + " nao foi deletada. Possivelmente este id nao existe");
			
	    return ResponseEntity.ok().body("Task deletada");
	}

	/** Converter entity para dto */
	private TaskDTO convertToDto(Task task) {
		TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
		return taskDTO;
	}

	/** Converter dto para entity */
	private Task convertToEntity(TaskDTO taskDTO) {
		Task task = modelMapper.map(taskDTO, Task.class);
		return task;
	}

}