package com.freeloader.restapi.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class AddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String streetAddress;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	
	//@OneToMany(mappedBy="address")
	//private UserEntity user;
	
	
	protected AddressEntity() {
		
	}
	
	
	public AddressEntity(String streetAddress, String city, String state, String zipCode, String country) {
		super();
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}


	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
        this.id = id;
    }


	@Override
	public int hashCode() {
		return Objects.hash(city, country, id, state, streetAddress, zipCode);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country) && id == other.id
				&& Objects.equals(state, other.state) && Objects.equals(streetAddress, other.streetAddress)
				&& Objects.equals(zipCode, other.zipCode);
	}


	@Override
	public String toString() {
		return "AddressEntity [id=" + id + ", streetAddress=" + streetAddress + ", city=" + city + ", state=" + state
				+ ", zipCode=" + zipCode + ", country=" + country + "]";
	}

}
