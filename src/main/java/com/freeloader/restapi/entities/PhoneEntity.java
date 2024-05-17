package com.freeloader.restapi.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PHONE")
public class PhoneEntity {
	
	@Id()
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private String phoneNumber;
	private String type;
	
	@ManyToOne
	private UserEntity user;

	protected PhoneEntity() {

	}

	public PhoneEntity(String phoneNumber, String type) {
		super();
		this.phoneNumber = phoneNumber;
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PhoneEntity phone = (PhoneEntity) o;
		return id == phone.id && phoneNumber.equals(phone.phoneNumber) && type.equals(phone.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, phoneNumber, type);
	}

	@Override
	public String toString() {
		return "PhoneEntity [id=" + id + ", phoneNumber=" + phoneNumber + ", type=" + type + "]";
	}

}
