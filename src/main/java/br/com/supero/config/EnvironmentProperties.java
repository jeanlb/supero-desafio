package br.com.supero.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Classe para acessar as propriedades da aplicacao (application.properties).
 * 
 * Web Source: https://better-coding.com/spring-how-to-autowire-bean-in-a-static-class/
 */
@Configuration
public class EnvironmentProperties {
	
	@Value("${secret.key}")
	private String secretKey;
	
	@Value("${timezone}")
	private String timeZone;
	
	@Value("${memcached.host}")
	private String memcachedHost;
	
	@Value("${memcached.port}")
	private String memcachedPort;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getMemcachedHost() {
		return memcachedHost;
	}

	public void setMemcachedHost(String memcachedHost) {
		this.memcachedHost = memcachedHost;
	}

	public int getMemcachedPort() {
		return Integer.valueOf(memcachedPort);
	}

	public void setMemcachedPort(String memcachedPort) {
		this.memcachedPort = memcachedPort;
	}

}