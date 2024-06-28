package com.freeloader.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.freeloader.restapi.controller.SearchParameters;
import com.freeloader.restapi.entities.AddressEntity;
import com.freeloader.restapi.entities.PhoneEntity;
import com.freeloader.restapi.entities.UserEntity;
import com.freeloader.restapi.exception.UserNotFoundException;
import com.freeloader.restapi.model.Address;
import com.freeloader.restapi.model.Phone;
import com.freeloader.restapi.model.UserDetails;
import com.freeloader.restapi.repository.AddressRepository;
import com.freeloader.restapi.repository.PhoneRepository;
import com.freeloader.restapi.repository.UserPagingRepository;
import com.freeloader.restapi.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	@Autowired
	private final AddressRepository addressRepository;
	private final PhoneRepository phoneRepository;
	private final UserPagingRepository userPagingRepository;
	
	public UserService(UserRepository userRepository, 
			AddressRepository addressRepository, 
			PhoneRepository phoneRepository,
			UserPagingRepository userPagingRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.phoneRepository = phoneRepository;
		this.userPagingRepository = userPagingRepository;
	}
	
	public List<UserDetails> retrieveAllUsers(Pageable pageable) {
		
		Page<UserEntity> all = userPagingRepository.findAll(pageable);
		all.forEach(user -> {
			System.out.println(user);
		});
		
		
		List<UserDetails> allUserDetails = convertUserEntitiesToUserDetails(all.getContent());
		return allUserDetails;
	}
	
	public UserDetails retriveUser(long id) throws UserNotFoundException {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (!userEntity.isPresent()) {
			throw new UserNotFoundException();
		}
		return convertUserEntitiesToUserDetails(List.of(userEntity.get())).get(0);
	}

	
	private List<UserDetails> convertUserEntitiesToUserDetails(List<UserEntity> allUsers) {
		List<UserDetails> userDetailsList = new ArrayList<>();
		for (UserEntity userEntity : allUsers) {
			Address address = convertAddressEntityToAddress(userEntity.getAddress());
			List<PhoneEntity> phonesEntities = userEntity.getPhones();
			List<Phone> phones = convertPhoneEntiriesToPhones(phonesEntities);
			UserDetails userDetails = new UserDetails(userEntity.getFirstName(), 
					userEntity.getLastName(), userEntity.getEmail(), 
					address, phones);
			userDetailsList.add(userDetails);
			
			
		}
		return userDetailsList;
	}

	private Address convertAddressEntityToAddress(AddressEntity addressEntity) {
        if (addressEntity == null) { return null; }
        return new Address(addressEntity.getStreetAddress(), addressEntity.getCity(), 
                addressEntity.getState(), addressEntity.getZipCode(), addressEntity.getCountry());
	}

	private List<Phone> convertPhoneEntiriesToPhones(List<PhoneEntity> phones) {
		List<Phone> phoneList = new ArrayList<>();
		for (PhoneEntity phoneEntity : phones) {
			phoneList.add(new Phone(phoneEntity.getPhoneNumber(), phoneEntity.getType()));
		}
		
		return phoneList;
		
	}

	public long createUser(UserDetails userDetails) {
		UserEntity addedUser = userRepository.save(convertUserDetailsToUserEntity(userDetails));
		return addedUser.getId();
	}
	
	public List<UserDetails> findUsersByPhoneNumber(SearchParameters searchParameters) {
		return convertUserEntitiesToUserDetails(userRepository.findAll().stream()
				.filter(user -> searchParameters.phoneNumber() == null || user.getPhones().stream().anyMatch(phone -> phone.getPhoneNumber().equals(searchParameters.phoneNumber())))
				.filter(user -> searchParameters.streetAddress() == null || user.getAddress().getStreetAddress().contains(searchParameters.streetAddress()))
				.filter(user -> searchParameters.city() == null || user.getAddress().getCity().contains(searchParameters.city()))
				.filter(user -> searchParameters.email() == null || user.getEmail().equalsIgnoreCase(searchParameters.email()))
				.filter(user -> searchParameters.firstName() == null || user.getFirstName().equalsIgnoreCase(searchParameters.firstName()))
				.filter(user -> searchParameters.lastName() == null || user.getLastName().equalsIgnoreCase(searchParameters.lastName()))
				.collect(Collectors.toList()));
	}

	private UserEntity convertUserDetailsToUserEntity(UserDetails userDetails) {
		UserEntity userEntity = new UserEntity(userDetails.firstName(), userDetails.lastName(), userDetails.email());
		maybeAddAddressEntity(userEntity, userDetails);
		maybeAddPhoneEntities(userEntity, userDetails);
		return userEntity;
	}

	private void maybeAddPhoneEntities(UserEntity userEntity, UserDetails userDetails) {
		if (userDetails.phones() == null) {  return; }
		if (userDetails.phones().isEmpty()) { return; }
		
		List<PhoneEntity> phoneEntities = new ArrayList<>();
		for (Phone phone : userDetails.phones()) {
			PhoneEntity phoneEntity = new PhoneEntity(phone.phoneNumber(), phone.type());
			phoneRepository.save(phoneEntity);
			phoneEntities.add(phoneEntity);

		}
		userEntity.setPhones(phoneEntities);
	}

	private void maybeAddAddressEntity(UserEntity userEntity, UserDetails userDetails) {
		if (userDetails.address() == null) { return; }
		
		Address address = userDetails.address();
		AddressEntity addressEntity = new AddressEntity(address.streetAddress(), address.city(), address.state(),
				address.zipCode(), address.country());
		maybeAddAddressEntityToDB(addressEntity);
		userEntity.setAddress(addressEntity);
	}

	private void maybeAddAddressEntityToDB(AddressEntity addressEntity) {
//		if (addressRepository.existsByStreetAddressAndCityAndStateAndCountryAndZipCode(addressEntity.getStreetAddress(),
//				addressEntity.getCity(), 
//				addressEntity.getState(), 
//				addressEntity.getCountry(),
//				addressEntity.getZipCode())) { return; }
		
		System.out.println(addressEntity);
		Optional<AddressEntity> addressFromDB = addressRepository.findByStreetAddressAndCityAndStateAndCountryAndZipCode(
				addressEntity.getStreetAddress(), addressEntity.getCity(), addressEntity.getState(),
				addressEntity.getCountry(), addressEntity.getZipCode());
		addressFromDB
				.ifPresentOrElse(address -> { addressEntity.setId(address.getId()); }, 
						() -> { addressRepository.save(addressEntity); 
					});
		//addressRepository.save(addressEntity);
	}
}
	

