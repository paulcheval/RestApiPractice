package com.freeloader.restapi.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.restapi.entities.AddressEntity;

@Repository
public interface AddressRepository extends ListCrudRepository<AddressEntity, Long> {

	boolean existsByStreetAddressAndCityAndStateAndCountryAndZipCode(String street, String city, String state, String country, String zipCode);

	Optional<AddressEntity> findByStreetAddressAndCityAndStateAndCountryAndZipCode(String street, String city, String state, String country, String zipCode);

}
