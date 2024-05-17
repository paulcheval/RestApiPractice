package com.freeloader.restapi.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.restapi.entities.UserEntity;

@Repository
public interface UserRepository extends ListCrudRepository<UserEntity, Long> {
	
//	List<User> findByEmailIgnoreCase(String email);
//
//	User findById(long id);
//
//	boolean existsByEmail(String email);
//
//	void deleteByEmail(String email);
	
	//List<User> findByPhoneNumberIgnoreCase(String phone);
}
