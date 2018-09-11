package br.com.supero.model.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.supero.encrypt.CipherEncryptURLParameter;

@JsonIgnoreProperties({ "id", "dataCriacao", "dataModificacao" }) // nao exibir estes atributos no json
public class TaskDTO implements AbstractDTO, Serializable {
	
	private static final long serialVersionUID = -3382642838816919153L;
	private static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	// Atributos
	private Long id;
	private String titulo;
	private String descricao;
	private Boolean statusConcluido;
	private Date dataCriacao;
	private Date dataModificacao;
	
	public TaskDTO(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public TaskDTO() {}
	
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
		if (this.id != null) {
			return CipherEncryptURLParameter
					.getInstance().encrypt(String.valueOf(this.id));
		}
		return null;
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
		if (idEncriptado != null && !idEncriptado.isEmpty()) {
			this.id = Long.valueOf(CipherEncryptURLParameter
					.getInstance().decrypt(idEncriptado.toString()));
		}
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
	
	@Override
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
	
	@Override
	public String toString() {
		return "TaskDTO ID: " + id
				+ " titulo: " + titulo
				+ " descricao: " + descricao;
	}
	
}