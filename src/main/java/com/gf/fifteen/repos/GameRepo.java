package com.gf.fifteen.repos;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.gf.fifteen.entities.game.GameEntity;

public interface GameRepo extends GraphRepository<GameEntity> {
	GameEntity findById(final String id);
}
