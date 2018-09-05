package br.com.supero.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe utilitaria para pegar propriedades do ambiente (application.properties)
 * 
 * Adapted from: https://www.baeldung.com/inject-properties-value-non-spring-class
 * 
 */
public class PropertiesLoader {
	
	private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

	/**
	 * Retorna o valor de uma propriedade do arquivo padrao
	 * de propriedades (application.properties)
	 * 
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		try {
			return loadProperties().getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Retorna uma instancia de Properties passando
	 * como parametro o nome do arquivo de propriedades
	 * 
	 * @param propertiesFileName
	 * @return Properties
	 */
	public static Properties loadProperties(String propertiesFileName) throws IOException {
		Properties configuration = new Properties();
		InputStream inputStream = PropertiesLoader.class
				.getClassLoader()
				.getResourceAsStream(propertiesFileName);
		configuration.load(inputStream);
		inputStream.close();
		return configuration;
	}
	
	/**
	 * Retorna uma instancia de Properties passando
	 * como parametro o nome do arquivo de propriedades
	 * padrao (application.properties)
	 * 
	 * @return Properties
	 */
	private static Properties loadProperties() throws IOException {
		return loadProperties(DEFAULT_PROPERTIES_FILE);
	}

}