package br.com.supero.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.supero.model.entity.Task;

@Repository
public interface TaskDAO extends JpaRepository<Task, Long> {
	
	List<Task> findAllByOrderByIdDesc();
	
}