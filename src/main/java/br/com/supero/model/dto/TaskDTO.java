package br.com.supero.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskDTO {
	
	private static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	
	// Atributos
	private Long id;
	private String titulo;
	private String descricao;
	private String status;
	private Date dataCriacao;
	private Date dataModificacao;

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

	public String getDataCriacao() {
		if (dataCriacao != null) {
			return formatoData.format(dataCriacao);
		}
		return null;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getDataModificacao() {
		if (dataModificacao != null) {
			return formatoData.format(dataModificacao);
		}
		return null;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status ? TaskEnum.CONCLUIDO.toString() : TaskEnum.EM_ANDAMENTO.toString();
	}

}