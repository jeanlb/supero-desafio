package br.com.supero.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.supero.model.dto.TaskDTO;
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

	private String memcachedHost = "localhost";
	private int memcachedPort = 11211;
	private MemcachedClient memcachedClient;
	
	public Memcached() {
		connect();
	}

	public void putInCache(String key, Object value) {
		memcachedClient.set(key, 3600, value); // (3600 - expiry time in seconds)
	}
	
	@SuppressWarnings("unchecked")
	public void appendInCacheList(String key, Object value) {
		List<Object> cachedList = (List<Object>) getInCache(key);
		cachedList.add(0, value);
		memcachedClient.replace(key, 3600, cachedList);
	}

	public Object getInCache(String key) {
		return memcachedClient.get(key);
	}

	public void deleteFromCache(String key) {
		memcachedClient.delete(key);
	}
	
	public void connect() {
		try {
			memcachedClient = new MemcachedClient(new InetSocketAddress(
					memcachedHost, memcachedPort));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		memcachedClient.shutdown();
	}

	
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
	
}