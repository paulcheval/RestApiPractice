package com.freeloader.restapi.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.restapi.entities.AddressEntity;

@Repository
public interface AddressRepository extends ListCrudRepository<AddressEntity, Long> {


	

}
