package br.com.supero;



import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SuperoDesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperoDesafioApplication.class, args);
	}

	/**
	 * Configuracao de Bean utilizado para converter entity para dto e vice-versa.
	 * Dependencia adicionada ao Maven (org.modelmapper).
	 * Fonte: https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	/** Habilitar o CORS globalmente */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/task/**").allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
			}
		};
	}
	
}