package br.com.supero.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

/**
 * Fabrica para criacao de AbstractDTO
 * 
 * @author jean
 *
 */
public class AbstractDTOFactory {
	
	private static ModelMapper modelMapper; // dependencia utilizada para converter tipos de objetos
	
	/* 
	 * Metodo utilizado para que classes gerenciadas pelo Spring injetem a dependencia 
	 * ModelMapper nesta classe, uma vez que ela eh estatica e nao gerenciada pelo Spring
	 */
	public static void setModelMapper(ModelMapper modelMapper) {
		AbstractDTOFactory.modelMapper = modelMapper;
	}
	
	public static List<AbstractDTO> createAbstractDTOFromObjectList(List<Object> objects) {
		
		List<AbstractDTO> dtoList = new ArrayList<AbstractDTO>();
		
		for (Object obj : objects) {
			AbstractDTO dto = createAbstractDTOFromObject(obj);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public static AbstractDTO createAbstractDTOFromObject(Object object) {
		
		AbstractDTO dto = null;
		
		switch (object.getClass().getName()) {
		
			case "br.com.supero.model.dto.TaskDTO":
				dto = convertObjectToTaskDTO(object);
				System.out.println("TaskDTO >>>> " + dto.toString());
				break;
	
			default:
				break;
		}
		
		return dto;
	}
	
	/** 
	 * Converter object para TaskDTO 
	 * 
	 * @param object
	 * @return TaskDTO
	 */
	private static TaskDTO convertObjectToTaskDTO(Object object) {
		TaskDTO taskDTO = modelMapper.map(object, TaskDTO.class);
		return taskDTO;
	}

}