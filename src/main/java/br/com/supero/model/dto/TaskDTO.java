package br.com.supero.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.supero.encrypt.CipherEncryptURLParameter;


public class TaskDTO {
	
	private static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	// Atributos
	private Long id;
	private String titulo;
	private String descricao;
	private Boolean statusConcluido;
	private Date dataCriacao;
	private Date dataModificacao;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	/** Encriptar id para que o seu valor real nao seja exibido em urls e views */
	public String getIdEncriptado() {
		return CipherEncryptURLParameter.encrypt(String.valueOf(this.id));
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
	
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public String getDataCriacaoFormatada() {
		if (dataCriacao != null) {
			return formatoData.format(dataCriacao);
		}
		return "";
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Date getDataModificacao() {
		return dataModificacao;
	}

	public String getDataModificacaoFormatada() {
		if (dataModificacao != null) {
			return formatoData.format(dataModificacao);
		}
		return "";
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public Boolean isStatusConcluido() {
		return statusConcluido;
	}

	public void setStatusConcluido(Boolean concluido) {
		this.statusConcluido = concluido;
	}
	
	public String getStatusConcluidoFormatado() {
		return isStatusConcluido() ? StatusConcluidoEnum.SIM.toString() : StatusConcluidoEnum.NAO.toString();
	}
	
}