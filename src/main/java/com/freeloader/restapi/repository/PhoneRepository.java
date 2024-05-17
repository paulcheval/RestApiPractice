package com.freeloader.restapi.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.restapi.entities.AddressEntity;
import com.freeloader.restapi.entities.PhoneEntity;

@Repository
public interface PhoneRepository extends ListCrudRepository<PhoneEntity, Long> {


	

}
