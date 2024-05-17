package com.freeloader.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freeloader.restapi.entities.AddressEntity;
import com.freeloader.restapi.entities.PhoneEntity;
import com.freeloader.restapi.entities.UserEntity;
import com.freeloader.restapi.model.Address;
import com.freeloader.restapi.model.Phone;
import com.freeloader.restapi.model.UserDetails;
import com.freeloader.restapi.repository.AddressRepository;
import com.freeloader.restapi.repository.PhoneRepository;
import com.freeloader.restapi.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	@Autowired
	private final AddressRepository addressRepository;
	private final PhoneRepository phoneRepository;
	
	public UserService(UserRepository userRepository, AddressRepository addressRepository, PhoneRepository phoneRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.phoneRepository = phoneRepository;
	}
	
	public List<UserDetails> retrieveAllUsers() {
		
		List<UserEntity> allUsers = userRepository.findAll();
		allUsers.forEach(user -> {
			System.out.println(user);
		});
		
		List<UserDetails> allUserDetails = convertUserEntitiesToUserDetails(allUsers);
		return allUserDetails;
	}
	
	public UserDetails retriveUser(long id) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (!userEntity.isPresent()) {
			throw new RuntimeException("User not found");
		}
		return convertUserEntitiesToUserDetails(List.of(userEntity.get())).get(0);
	}

	
	private List<UserDetails> convertUserEntitiesToUserDetails(List<UserEntity> allUsers) {
		List<UserDetails> userDetailsList = new ArrayList<>();
		for (UserEntity userEntity : allUsers) {
			AddressEntity addressEntity = userEntity.getAddress();
			Address address = convertAddressEntityToAddress(addressEntity);
			List<PhoneEntity> phonesEntities = userEntity.getPhones();
			List<Phone> phones = convertPhoneEntiriesToPhones(phonesEntities);
			UserDetails userDetails = new UserDetails(userEntity.getFirstName(), 
					userEntity.getLastName(), userEntity.getEmail(), 
					address, phones);
			userDetailsList.add(userDetails);
			
			
		}
		// TODO Auto-generated method stub
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

	public UserDetails createUser(UserDetails userDetails) {
		UserEntity addedUser = userRepository.save(convertUserDetailsToUserEntity(userDetails));
		return convertUserEntitiesToUserDetails(List.of(addedUser)).get(0);
	}

	private UserEntity convertUserDetailsToUserEntity(UserDetails userDetails) {
		UserEntity userEntity = new UserEntity(userDetails.firstName(), userDetails.lastName(), userDetails.email());
		if (userDetails.address() != null) {
			Address address = userDetails.address();
			AddressEntity addressEntity = new AddressEntity(address.streetAddress(),
					address.city(), address.state(), address.zipCode(),
					address.country());
			addressRepository.save(addressEntity);
			userEntity.setAddress(addressEntity);
		}
		if (userDetails.phones() != null && !userDetails.phones().isEmpty()) {
			List<Phone> phones = userDetails.phones();
			List<PhoneEntity> phoneEntities = new ArrayList<>();
			for (Phone phone : phones) {
				PhoneEntity phoneEntity = new PhoneEntity(phone.phoneNumber(), phone.type());
				phoneRepository.save(phoneEntity);
				phoneEntities.add(phoneEntity);
				
			}
			//phoneRepository.saveAll(phoneEntities);
			userEntity.setPhones(phoneEntities);
		}
		return userEntity;
	}
}
	

