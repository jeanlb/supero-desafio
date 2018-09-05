package br.com.supero.encrypt; 

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

import br.com.supero.config.EnvironmentProperties;

/**
 * Simple encrypt/decrypt util for url parameters
 * Web Sources: 
 * https://gist.github.com/cxubrix/4316635
 * https://better-coding.com/spring-how-to-autowire-bean-in-a-static-class/
 * 
 * Ps.: Este codigo teve algumas adaptacoes em relacao ao original 
 * 
 * Why not dependency injection with this class, with this case?
 * See: https://softwareengineering.stackexchange.com/questions/360525/dependency-injection-vs-static-methods
 * 
 * "There is no reason why this needs to be injected. This is just a function, it has no dependencies, so just 
 * call it. It can even be static if you want as it looks to be pure. One can write unit tests against this 
 * with no difficulty. If it is used in other classes, unit tests can still be written."
 *
 */
public class CipherEncryptURLParameter {
	
	private static String SECRET_KEY = "S3CR3TK3YtoURLP4R4M3T3R";
	private static final byte[]	SALT = { (byte) 0x21, (byte) 0x21, (byte) 0xF0, (byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A, (byte) 0x75 }; // some random salt
	private static final int ITERATION_COUNT = 31;
	
	private static EnvironmentProperties environmentProperties;
	 
    public static void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
    	CipherEncryptURLParameter.environmentProperties = environmentProperties;
    }

	private CipherEncryptURLParameter() {}

	public static String encrypt(String input) {
		
		if (input == null) {
			throw new IllegalArgumentException();
		}
		
		try {
			
			SECRET_KEY = environmentProperties != null ? environmentProperties.getSecretKey() : SECRET_KEY;
			
			KeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT, ITERATION_COUNT);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

			byte[] enc = ecipher.doFinal(input.getBytes());

			String res = new String(Base64.encodeBase64(enc));
			// escapes for url
			res = res.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A");

			return res;
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String decrypt(String inputEncripted) {
		
		if (inputEncripted == null) {
			return null;
		}
		
		try {
			
			SECRET_KEY = environmentProperties != null ? environmentProperties.getSecretKey() : SECRET_KEY;
			
			String input = inputEncripted.replace("%0A", "\n").replace("%25", "%").replace('_', '/').replace('-', '+');

			byte[] dec = Base64.decodeBase64(input.getBytes());

			KeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT, ITERATION_COUNT);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

			byte[] decoded = dcipher.doFinal(dec);

			String result = new String(decoded);
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}