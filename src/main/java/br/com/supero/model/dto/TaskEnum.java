package br.com.supero.model.dto;

public enum TaskEnum {

	CONCLUIDO {
		public String toString() {
			return "Concluido";
		}
	},

	EM_ANDAMENTO {
		public String toString() {
			return "Em andamento";
		}
	}

}