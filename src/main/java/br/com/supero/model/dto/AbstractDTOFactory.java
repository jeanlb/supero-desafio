package br.com.supero.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Fabrica para criacao de AbstractDTO
 * 
 * @author jean
 *
 */
@Component
public class AbstractDTOFactory {
	
	@Autowired
	private ModelMapper modelMapper; // dependencia utilizada para converter tipos de objetos
	
	public List<AbstractDTO> createAbstractDTOFromObjectList(List<Object> objects) {
		
		List<AbstractDTO> dtoList = new ArrayList<AbstractDTO>();
		
		for (Object obj : objects) {
			AbstractDTO dto = createAbstractDTOFromObject(obj);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public AbstractDTO createAbstractDTOFromObject(Object object) {
		
		AbstractDTO dto = null;
		
		switch (object.getClass().getName()) {
		
			case "br.com.supero.model.dto.TaskDTO":
				dto = convertObjectToTaskDTO(object);
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
	private TaskDTO convertObjectToTaskDTO(Object object) {
		TaskDTO taskDTO = modelMapper.map(object, TaskDTO.class);
		return taskDTO;
	}

}