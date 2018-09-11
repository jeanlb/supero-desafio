package br.com.supero.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.supero.config.EnvironmentProperties;
import br.com.supero.model.dto.AbstractDTO;
import br.com.supero.model.dto.AbstractDTOFactory;
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
	private int expirationTime;
	
	@Autowired
	private EnvironmentProperties environmentProperties;
	
	@Autowired
	private AbstractDTOFactory abstractDTOFactory;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * O metodo na classe @Component que tiver a anotacao @PostConstruct
	 * eh chamado apos o Spring inicializar todas as dependencias da aplicacao,
	 * incluindo as classe de configuracao, tal como EnvironmentProperties.
	 * 
	 * Web Source: https://medium.com/@dmarko484/spring-boot-startup-init-through-postconstruct-765b5a5c1d29
	 */
	@PostConstruct
	public void init() {
		log.info("Memcached connection initializing..");
		checkConnection();
		this.expirationTime = environmentProperties.getMemcachedExpirationTime();
	}
	
	/*
	 * Metodo com a anotacao @PreDestroy eh chamado antes da aplicacao ser encerrada
	 */
	@PreDestroy
    public void destroy() {
		log.info("Memcached connection shutdowning..");
    	disconnect(); // desconectar aplicacao do servidor de cache
    }
	
	/**
	 * Criar cache
	 * 
	 * @param key, value
	 */
	public void putInCache(String key, Object value) {
		memcachedClient.set(key, this.expirationTime, value);
	}
	
	/**
	 * Adicionar ao cache
	 * 
	 * @param key, value
	 */
	@SuppressWarnings("unchecked")
	public void appendInCache(String key, Object value) {
		
		Object cachedObject = getInCache(key);
		
		if (cachedObject instanceof List) {
			List<Object> cachedList = (List<Object>) cachedObject;
			cachedList.add(0, value);
			memcachedClient.replace(key, this.expirationTime, cachedList);
		} else {
			memcachedClient.append(key, value);
		}
	}
	
	/**
	 * Atualizar cache
	 * 
	 * @param key, value
	 */
	@SuppressWarnings("unchecked")
	public void updateCache(String key, Object value) {
		Object cachedObject = getInCache(key);
		
		if (cachedObject instanceof List) {
			
			// converter lista de objetos para lista de AbstractDTO
			List<AbstractDTO> dtoList = abstractDTOFactory
					.createAbstractDTOFromObjectList((List<Object>) cachedObject);
			
			AbstractDTO cachedDTO = abstractDTOFactory.createAbstractDTOFromObject(value);
			
			// atualizar elemento (AbstractDTO) na lista
			dtoList = dtoList.stream()
				    .map(dto -> dto.getId().equals(cachedDTO.getId()) ? cachedDTO : dto)
				    .collect(Collectors.toList());
			
			// atualizar cache 
			memcachedClient.replace(key, this.expirationTime, dtoList);
		} else {
			clearCache(key);
		}
	}

	/**
	 * Retornar elementos (values) do cache
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getInCache(String key) {
		return memcachedClient.get(key);
	}
	
	/**
	 * Retornar elemento (value) contido no cache
	 * 
	 * @param key, id
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public Object getInCache(String key, Long id) {
		Object cachedObject = getInCache(key);
		
		if (cachedObject instanceof List)  {
			
			// converter lista de objetos para lista de AbstractDTO
			List<AbstractDTO> dtoList = abstractDTOFactory
					.createAbstractDTOFromObjectList((List<Object>) cachedObject);
			
			// pegar elemento no cache cujo id seja igual ao passado como parametro
			cachedObject = dtoList.stream()
				    .filter(dto -> dto.getId().equals(id))
				    .findFirst()
				    .get();
			
			return cachedObject;
		}
		
		return null;
	}

	/**
	 * Deletar elemento do cache
	 * 
	 * @param key, value
	 */
	@SuppressWarnings("unchecked")
	public void deleteFromCache(String key, Long id) {
		Object cachedObject = getInCache(key);
		
		if (cachedObject instanceof List) {
			
			// converter lista de objetos para lista de AbstractDTO
			List<AbstractDTO> dtoList = abstractDTOFactory
					.createAbstractDTOFromObjectList((List<Object>) cachedObject);
			
			// remover elemento (AbstractDTO) da lista
			dtoList.removeIf(dto -> dto.getId().equals(id));
			
			// atualizar cache 
			memcachedClient.replace(key, this.expirationTime, dtoList);
		} else {
			clearCache(key);
		}
	}
	
	/**
	 * Limpar o cache passando uma chave identificadora do cache
	 * 
	 * @param key
	 */
	public void clearCache(String key) {
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
	
	/**
	 * Checar se ha conexao aberta e se nao houver conectar
	 */
	private void checkConnection() {
		boolean isConnected = (memcachedClient != null 
				&& memcachedClient.getConnection().isAlive());
		if (!isConnected) connect();
	}
	
	/**
	 * Desconectar do servidor Memcached.
	 */
	private void disconnect() {
//		memcachedClient.flush(); // limpa todos os registros no cache
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