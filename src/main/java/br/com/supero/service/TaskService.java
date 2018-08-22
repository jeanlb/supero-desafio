package br.com.supero.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.supero.encrypt.CipherEncryptURLParameter;
import br.com.supero.model.dao.TaskDAO;
import br.com.supero.model.dto.TaskDTO;
import br.com.supero.model.entity.Task;

/**
 * 
 * @author Jean
 * 
 * Service: camada para regras de negocio, validacoes, conversoes e acesso ao DAO
 *
 */

@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private ModelMapper modelMapper; // dependencia utilizada para converter entity para dto

	public TaskDTO inserir(TaskDTO taskDTO) {
		
		Task task = convertToEntity(taskDTO);

		Task taskInserida = new Task(task.getTitulo(), task.getDescricao());
		taskInserida.setStatusConcluido(false);
		taskInserida.setDataCriacao(new Date());

		taskDAO.saveAndFlush(taskInserida);

		return convertToDto(taskInserida);
	}
	
	public TaskDTO atualizar(TaskDTO taskDTO) {		
		
		Task task = convertToEntity(taskDTO);
		Task taskAtualizada = getTaskPorId(task.getId());
		
		taskAtualizada.setTitulo(task.getTitulo());
		taskAtualizada.setDescricao(task.getDescricao());
		taskAtualizada.setStatusConcluido(task.isStatusConcluido());
		taskAtualizada.setDataModificacao(new Date());
		
		taskDAO.saveAndFlush(taskAtualizada);
		
		return convertToDto(taskAtualizada);
	}

	public void atualizarStatus(String idEncrypted, Boolean statusConcluido) {
		
		Long idDecrypted = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncrypted.toString()));
		Task task = getTaskPorId(idDecrypted);
		
		task.setStatusConcluido(statusConcluido);
		task.setDataModificacao(new Date());
		
		taskDAO.saveAndFlush(task);
	}
	
	public TaskDTO getTaskDTOPorId(String idEncrypted) {
		Long idDecrypted = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncrypted.toString()));
		
		Task task = getTaskPorId(idDecrypted);
		return convertToDto(task);
	}
	
	private Task getTaskPorId(Long id) {
		Task task = taskDAO.findById(id).get();
		return task;
	}
	
	public List<TaskDTO> listar() {
		
		List<Task> tasks = taskDAO.findAll();
		
		List<TaskDTO> listTaskDTO = tasks.stream()
				.map(task -> convertToDto(task))
				.collect(Collectors.toList());
		
		return listTaskDTO;
	}

	public boolean deletar(String idEncrypted) {
		try {
			Long idDecrypted = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncrypted.toString()));
			taskDAO.deleteById(idDecrypted);
			return true;
		} catch (EmptyResultDataAccessException e) {
			LoggerFactory.getLogger(this.getClass())
				.error("ERRO AO TENTAR DELETAR TASK: " + e.getMessage());
			return false;
		}
	}
	
	/* ==== Conversores ==== */
	
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