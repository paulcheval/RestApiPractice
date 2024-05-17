package com.freeloader.restapi.client.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class BookingHealthStatus {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(status);
	}



	public BookingHealthStatus(String status) {
		super();
		this.status = status;
	}
	public BookingHealthStatus() {
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingHealthStatus other = (BookingHealthStatus) obj;
		return Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "BookingHealthStatus [status=" + status + "]";
	}

	
	
}
