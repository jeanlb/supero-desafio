package br.com.supero.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.supero.config.EnvironmentProperties;
import net.spy.memcached.MemcachedClient;

/**
 * Classe componente para gerenciar objetos armazenados no cache. 
 * Utilizando Memcached como servidor de cache e spymemcached como cliente Java para o Memcached.
 * 
 * Adapted from: 
 * https://blog.imaginea.com/testing-distributed-memcache-using-spymemcached-in-java/
 */

@Component
public class Memcached {

	private MemcachedClient memcachedClient;
	
	@Autowired
	private EnvironmentProperties environmentProperties;
	
	public void putInCache(String key, Object value) {
		checkConnection();
		memcachedClient.set(key, 3600, value); // (3600 - expiry time in seconds)
	}
	
	@SuppressWarnings("unchecked")
	public void appendInCache(String key, Object value) {
		checkConnection();
		
		Object cachedObject = getInCache(key);
		
		if (cachedObject instanceof List) {
			List<Object> cachedList = (List<Object>) cachedObject;
			cachedList.add(0, value);
			memcachedClient.replace(key, 3600, cachedList);
		} else {
			memcachedClient.append(key, value);
		}
	}

	public Object getInCache(String key) {
		checkConnection();
		return memcachedClient.get(key);
	}

	public void deleteFromCache(String key) {
		checkConnection();
		memcachedClient.delete(key);
	}
	
	/**
	 * Instanciar MemcachedClient e criar a conexao com o servidor Memcached.
	 */
	private void connect() {
		
		String memcachedHost = environmentProperties.getMemcachedHost();
		int memcachedPort = environmentProperties.getMemcachedPort();
		
		try {
			memcachedClient = new MemcachedClient(new InetSocketAddress(
					memcachedHost, memcachedPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void checkConnection() {
		boolean isConnected = (memcachedClient != null 
				&& memcachedClient.getConnection().isAlive());
		if (!isConnected) connect();
	}
	
	public void disconnect() {
		memcachedClient.shutdown();
	}

	/*
	public static void main(String[] args) {
		
		Memcached test = new Memcached();
		test.putInCache("key", "abc");
		System.out.println(test.getInCache("key"));
		test.deleteFromCache("key");
		System.out.println(test.getInCache("key"));
		
		List<String> list = Arrays.asList("a", "b");
		test.putInCache("key", list);
		System.out.println(test.getInCache("key"));
		test.deleteFromCache("key");
		System.out.println(test.getInCache("key"));
		
		TaskDTO task1 = new TaskDTO("Task 1", "Descricao task 1");
		TaskDTO task2 = new TaskDTO("Task 2", "Descricao task 2");
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		tasks.add(task1);
		tasks.add(task2);
		test.putInCache("key", tasks);
		System.out.println(test.getInCache("key"));
		
		TaskDTO task3 = new TaskDTO("Task 3", "Descricao task 3");
		test.appendInCacheList("key", task3);
		System.out.println(test.getInCache("key"));
		
		test.deleteFromCache("key");
		System.out.println(test.getInCache("key"));
		
		for (int i = 0; i< 10; i++) {
		test.putInCache("key" + "_" + i , "value" + "_" + i);
		}
		for (int i = 0; i < 10; i++) {
		System.out.println("key _" +i + " - " + test.getInCache("key" + "_" + i));
		}
		test.deleteFromCache("key");
		System.out.println(test.getInCache("key"));
		
		test.disconnect();
	}
	*/
}