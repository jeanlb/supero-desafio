package br.com.supero.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String descricao;
	
	@Column(name = "status_concluido", nullable = false)
	private Boolean statusConcluido;
	
	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@Column(name = "data_modificacao")
	private Date dataModificacao;
	
	// Construtores
	public Task(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public Task() {}
	
	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean isStatusConcluido() {
		return statusConcluido;
	}

	public void setStatusConcluido(Boolean concluido) {
		this.statusConcluido = concluido;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}
	
	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
}