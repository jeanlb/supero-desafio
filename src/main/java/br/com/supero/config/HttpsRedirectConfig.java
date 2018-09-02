package br.com.supero.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Classe para fazer redirecionamento de requisicoes HTTP 
 * pela porta 8080 para HTTPS 8443
 * 
 * Web Source:
 * https://howtodoinjava.com/spring-boot/spring-boot-ssl-https-example/
 * https://www.thomasvitale.com/https-spring-boot-ssl-certificate/
 * https://drissamri.be/blog/java/enable-https-in-spring-boot/
 * https://stackoverflow.com/questions/47551951/embeddedservletcontainerfactory-tomcatembeddedservletcontainerfactory-cant-be-f
 * 
 * @author Jean
 *
 */
@Configuration
public class HttpsRedirectConfig {
	
	private final static String SECURITY_USER_CONSTRAINT = "CONFIDENTIAL";
	private final static String REDIRECT_PATTERN = "/*";
	private final static String CONNECTOR_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
	private final static String CONNECTOR_SCHEME = "http";
	
	@Autowired
	Environment environment;

	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint(SECURITY_USER_CONSTRAINT);
				
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern(REDIRECT_PATTERN);
				
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		
		return tomcat;
	}

	private Connector redirectConnector() {
		
		Connector connector = new Connector(CONNECTOR_PROTOCOL);
		connector.setScheme(CONNECTOR_SCHEME);
		connector.setSecure(false);
		
		/* Em componentes de configuracao (como esta classe) pegar as propriedades 
		 * do ambiente (environment) por @Autowired, pois a classe EnvironmentProperties
		 * foi definido como um componente de configuracao (@Configuration) e por isso
		 * lanca um erro caso seja utilizado nesta classe 
		 */
		int httpPort = Integer.valueOf(environment.getProperty("server.http.port"));
		int httpsRedirectPort = Integer.valueOf(environment.getProperty("server.port"));
		
		connector.setPort(httpPort);
		connector.setRedirectPort(httpsRedirectPort);
		
		return connector;
	}

}