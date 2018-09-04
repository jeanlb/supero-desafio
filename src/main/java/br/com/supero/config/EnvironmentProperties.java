package br.com.supero.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Classe para acessar as propriedades da aplicacao (application.properties).
 * Util para classes que nao estejam sendo gerenciadas pelo spring e que
 * necessitem, por exemplo, pegar o valor de alguma propriedade no application.properties
 * 
 * Web Source: https://stackoverflow.com/questions/19454289/spring-boot-environment-autowired-throws-nullpointerexception
 * https://www.moreofless.co.uk/spring-mvc-java-autowired-component-null-repository-service/
 */
@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
public class EnvironmentProperties implements EnvironmentAware {
	
	private static Environment environment;
	
	public static String getProperty(String key) {
		return environment.getProperty(key);
	}

	@Override
	public void setEnvironment(Environment env) {
		environment = env;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}