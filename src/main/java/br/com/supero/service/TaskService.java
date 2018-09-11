package br.com.supero.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.supero.cache.Memcached;
import br.com.supero.config.EnvironmentProperties;
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
	private EnvironmentProperties environmentProperties;
	
	@Autowired
	private Memcached cache;
	
	@Autowired
	private ModelMapper modelMapper; // dependencia utilizada para converter entity para dto
	

	public TaskDTO inserir(TaskDTO taskDTO) {
		
		Task task = convertToEntity(taskDTO);

		Task taskInserida = new Task(task.getTitulo(), task.getDescricao());
		taskInserida.setStatusConcluido(false);
		taskInserida.setDataCriacao(getDateFromTimeZoneId());

		taskDAO.saveAndFlush(taskInserida);
		
		taskDTO = convertToDto(taskInserida);
		cache.appendInCache("tasks", taskDTO);

		return taskDTO;
	}
	
	public TaskDTO atualizar(TaskDTO taskDTO) {		
		
		Task task = convertToEntity(taskDTO);
		Task taskAtualizada = getTaskPorId(task.getId());
		
		taskAtualizada.setTitulo(task.getTitulo());
		taskAtualizada.setDescricao(task.getDescricao());
		taskAtualizada.setStatusConcluido(task.isStatusConcluido());
		taskAtualizada.setDataModificacao(getDateFromTimeZoneId());
		
		taskDAO.saveAndFlush(taskAtualizada);
		
		cache.clearCache("tasks");
		
		return convertToDto(taskAtualizada);
	}

	public void atualizarStatus(Long id, Boolean statusConcluido) {
		
		Task task = getTaskPorId(id);
		
		task.setStatusConcluido(statusConcluido);
		task.setDataModificacao(getDateFromTimeZoneId());
		
		taskDAO.saveAndFlush(task);
		
		cache.clearCache("tasks");
	}
	
	public TaskDTO getTaskDTOPorId(Long id) {
		Task task = getTaskPorId(id);
		return convertToDto(task);
	}
	
	/* 
	 * Pq usar @Transactional na camada Service e nao no Repository ou Controller: 
	 * https://stackoverflow.com/questions/18498115/why-use-transactional-with-service-instead-of-with-controller
	 */
	@Transactional(readOnly = true)
	private Task getTaskPorId(Long id) {
		Task task = taskDAO.findById(id).get();
		return task;
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<TaskDTO> listar() {
		
		List<TaskDTO> listTaskDTO = (List<TaskDTO>) cache.getInCache("tasks");
		
		if (listTaskDTO == null || listTaskDTO.isEmpty()) {
			List<Task> tasks = taskDAO.findAllByOrderByIdDesc();
			
			listTaskDTO = tasks.stream()
					.map(task -> convertToDto(task))
					.collect(Collectors.toList());
			
			cache.putInCache("tasks", listTaskDTO);
		}
		
		return listTaskDTO;
	}

	public boolean deletar(Long id) {
		try {
			taskDAO.deleteById(id);
			
			cache.deleteFromCacheById("tasks", id);
			
			return true;
		} catch (EmptyResultDataAccessException e) {
			LoggerFactory.getLogger(this.getClass())
				.error("ERRO AO TENTAR DELETAR TASK: " + e.getMessage());
			return false;
		}
	}
	
	/** 
	 * Retorna a data e hora atual de uma timezone (fuso horario) definida 
	 * como uma propriedade de configuracao. Util para evitar que a time 
	 * zone da data a ser inserida/atualizada seja a do servidor.
	 * 
	 * Web Source: https://stackoverflow.com/questions/19431234/converting-between-java-time-localdatetime-and-java-util-date 
	 * 
	 * @return Date
	 */
	private Date getDateFromTimeZoneId() {
		
		String timezoneProperty = environmentProperties.getTimeZone();
		
		// caso propriedade timezone nao tenha um valor, eh usada a timezone do servidor
		ZoneId timezone = (timezoneProperty != null && !timezoneProperty.isEmpty())
				? ZoneId.of(timezoneProperty) : ZoneId.systemDefault();

		// converter LocalDateTime atual com timezone para Date
        LocalDateTime localDateTime = LocalDateTime.now(timezone);
        Date dateNow = java.sql.Timestamp.valueOf(localDateTime);
        
        return dateNow;
	}
	
	/* ==== Conversores ==== */
	
	/** 
	 * Converter entity para dto 
	 * 
	 * @return TaskDTO
	 */
	private TaskDTO convertToDto(Task task) {
		TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
		return taskDTO;
	}

	/** 
	 * Converter dto para entity 
	 *  
	 * @return Task
	 */
	private Task convertToEntity(TaskDTO taskDTO) {
		Task task = modelMapper.map(taskDTO, Task.class);
		return task;
	}
	
}