package br.com.supero.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	private final static int CONNECTOR_PORT = 8080;
	private final static int CONNECTOR_REDIRECT_PORT = 8443;

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
		connector.setPort(CONNECTOR_PORT);
		connector.setRedirectPort(CONNECTOR_REDIRECT_PORT);
		connector.setSecure(false);
		
		return connector;
	}

}