package com.freeloader.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.restapi.entities.UserEntity;

@Repository
public interface UserRepository extends ListCrudRepository<UserEntity, Long> {
	

	
}
