package br.com.supero;



import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SuperoDesafioApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SuperoDesafioApplication.class, args);
	}
	
	/**
	 * Metodo sobrescrito da classe SpringBootServletInitializer,
	 * cuja finalidade eh configurar a aplicacao para que,
	 * em modo de producao (war gerado), possa ser utilizada
	 * em containers externos (Wildfly, Tomcat, etc).
	 * 
	 * Adapted from: 
	 * http://www.mastertheboss.com/jboss-frameworks/spring/spring-boot-hello-world-on-wildfly
	 * 
	 * @param SpringApplicationBuilder
	 * @return SpringApplicationBuilder
	 */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SuperoDesafioApplication.class);
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