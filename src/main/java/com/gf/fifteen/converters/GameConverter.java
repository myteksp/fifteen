package com.gf.fifteen.converters;

import org.springframework.stereotype.Component;

import com.gf.fifteen.entities.dao.game.GameEntity;
import com.gf.fifteen.entities.dto.game.GameDTO;

@Component
public final class GameConverter {
	
	public final GameDTO convert(final GameEntity entity){
		if (entity == null)
			return null;
		final GameDTO result = new GameDTO();
		result.id = entity.id;
		result.state = entity.state;
		result.startDate = entity.startDate;
		result.endDate = entity.endDate;
		result.size = entity.size;
		result.position = entity.position;
		return result;
	}
	
	public final GameEntity convert(final GameDTO dto){
		if (dto == null)
			return null;
		final GameEntity result = new GameEntity();
		result.id = dto.id;
		result.state = dto.state;
		result.startDate = dto.startDate;
		result.endDate = dto.endDate;
		result.size = dto.size;
		result.position = dto.position;
		return result;
	}
}
