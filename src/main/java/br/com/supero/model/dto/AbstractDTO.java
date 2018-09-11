package br.com.supero.model.dto;

/**
 * Interface para ser implementada pelos DTOs.
 * Utilizada para se ter uma referencia ao id de um DTO
 * ao atualizar ou deletar um elemento no cache (Memcached).
 * 
 * @author jean
 *
 */
public interface AbstractDTO {
	
	Long getId();

}