package com.gf.fifteen.repos;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.gf.fifteen.entities.UserEntity;

public interface UserRepo extends GraphRepository<UserEntity>{
	UserEntity findByUserName(final String userName);
}
