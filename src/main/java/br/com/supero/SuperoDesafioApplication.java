package br.com.supero;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperoDesafioApplication {
	
	/**
	 * Configuracao de Bean utilizado para converter entity para dto e vice-versa.
	 * Dependencia adicionada ao Maven (org.modelmapper)
	 * 
	 */
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SuperoDesafioApplication.class, args);
	}
}