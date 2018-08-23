package br.com.supero.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.supero.encrypt.CipherEncryptURLParameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "id", "dataCriacao", "dataModificacao" }) // nao exibir estes atributos no json
public class TaskDTO {
	
	private static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	// Atributos
	private Long id;
	private String titulo;
	private String descricao;
	private Boolean statusConcluido;
	private Date dataCriacao;
	private Date dataModificacao;

	/* 
	 * Metodos para conversao e exibicao formatada de atributos no json.
	 * 
	 * Estes metodos sao dependentes dos getters e setters originais para 
	 * poderem, no momento da conversao entre entity e dto, pegar o tipo 
	 * do valor original e converter para o tipo retornado de cada um.
	 */
	
	/** 
	 * Encriptar id para que o seu valor real nao seja exibido em urls e views.
	 * 
	 * @return String
	 */
	public String getIdEncriptado() {
		return CipherEncryptURLParameter.encrypt(String.valueOf(this.id));
	}
	
	/**
	 * Receber a String com o id encriptado e decripta-lo,
	 * convertendo-o para o tipo e valor original (Long).
	 * Utilizado principalmente na atualizacao da task
	 * e na conversao de dto para entity.
	 * 
	 * @param idEncriptado
	 */
	public void setIdEncriptado(String idEncriptado) {
		this.id = Long.valueOf(CipherEncryptURLParameter.decrypt(idEncriptado.toString()));
	}
	
	public String getDataCriacaoFormatada() {
		if (dataCriacao != null) {
			return formatoData.format(dataCriacao);
		}
		return "";
	}
	
	public String getDataModificacaoFormatada() {
		if (dataModificacao != null) {
			return formatoData.format(dataModificacao);
		}
		return "";
	}
	
	public String getStatusConcluidoFormatado() {
		return isStatusConcluido() ? StatusConcluidoEnum.SIM.toString() : StatusConcluidoEnum.NAO.toString();
	}
	
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
	
	public Boolean isStatusConcluido() {
		return statusConcluido;
	}

	public void setStatusConcluido(Boolean concluido) {
		this.statusConcluido = concluido;
	}
	
}