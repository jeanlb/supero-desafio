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
	
	@Value("${timezone}")
	private String timeZone;
	
	@Value("${memcached.host}")
	private String memcachedHost;
	
	@Value("${memcached.port}")
	private String memcachedPort;
	
	@Value("${memcached.expiration.time}")
	private String memcachedExpirationTime;

	public String getTimeZone() {
		String defaultTimeZone = "America/Sao_Paulo";
		return (timeZone != null && !timeZone.isEmpty())
				? timeZone.trim() : defaultTimeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getMemcachedHost() {
		String defaultHost = "localhost";
		return (memcachedHost != null && !memcachedHost.isEmpty())
				? memcachedHost.trim() : defaultHost;
	}

	public void setMemcachedHost(String memcachedHost) {
		this.memcachedHost = memcachedHost;
	}

	public int getMemcachedPort() {
		int defaultPort = 11211;
		return (memcachedPort != null && !memcachedPort.isEmpty())
				? Integer.valueOf(memcachedPort.trim())
						: defaultPort;
	}

	public void setMemcachedPort(String memcachedPort) {
		this.memcachedPort = memcachedPort;
	}
	
	public int getMemcachedExpirationTime() {
		int defaultExpirationTime = 3600;
		return (memcachedExpirationTime != null && !memcachedExpirationTime.isEmpty())
				? Integer.valueOf(memcachedExpirationTime.trim())
						: defaultExpirationTime;
	}

	public void setMemcachedExpirationTime(String memcachedExpirationTime) {
		this.memcachedExpirationTime = memcachedExpirationTime;
	}

}