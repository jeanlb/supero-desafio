package br.com.supero;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.supero.cache.Memcached;

@SpringBootApplication
public class SuperoDesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperoDesafioApplication.class, args);
	}

	/**
	 * Configuracao de Bean utilizado para converter entity para dto e vice-versa.
	 * Dependencia adicionada ao Maven (org.modelmapper).
	 * Fonte: https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
	 * 
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
	
	/**
	 * Interceptar a inicializacao e encerramento da aplicacao.
	 * Util para adicionar algum comportamento necessario em algum dos casos.
	 * 
	 * Adapted from: https://www.logicbig.com/tutorials/spring-framework/spring-boot/destruction-callback.html
	 */
	@Bean
	OnInitShutdownApplication onInitShutdownApplication() {
        return new OnInitShutdownApplication();
    }
	
	private class OnInitShutdownApplication {
		
		@Autowired
		private Memcached cache; // com a injecao de dependencia o cache eh inicializado quando a aplicacao eh iniciada
		
        @PostConstruct
        public void init() {
            System.out.println("Initialing Spring Boot TaskList application.. Memcached connection initialized");
        }

        @PreDestroy
        public void destroy() {
        	System.out.println("Shutdowning Spring Boot TaskList application.. Memcached connection shutdowning..");
            cache.disconnect(); // desconectar aplicacao do servidor de cache
        }
    }
	
}