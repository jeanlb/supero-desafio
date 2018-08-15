package br.com.supero.service;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.supero.model.dao.TaskDAO;
import br.com.supero.model.entity.Task;

/**
 * 
 * @author Jean
 * 
 * Service: camada para regras de negocio, validacoes e acesso ao DAO
 *
 */

@Service
public class TaskService {

	@Autowired
	private TaskDAO taskDAO;

	public Task inserir(Task task) {

		Task taskInserida = new Task(task.getTitulo(), task.getDescricao());
		taskInserida.setStatus(false);
		taskInserida.setDataCriacao(new Date());

		taskDAO.saveAndFlush(taskInserida);

		return taskInserida;
	}

	public List<Task> listar() {
		return taskDAO.findAll();
	}
	
	public void atualizarStatus(Long id, Boolean status) {
		Task task = getTaskPorId(id);
		task.setStatus(status);
		task.setDataModificacao(new Date());
		
		taskDAO.saveAndFlush(task);
	}
	
	public Task getTaskPorId(Long id) {
		return taskDAO.findById(id).get();
	}

	public boolean deletar(Long id) {
		try {
			taskDAO.deleteById(id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			LoggerFactory.getLogger(this.getClass())
				.error("ERRO AO TENTAR DELETAR TASK: " + e.getMessage());
			return false;
		}
	}

}