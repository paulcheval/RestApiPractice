package com.freeloader.restapi.util;

public enum ServiceStatus {
	
	UP("UP"), 
	DOWN("DOWN"),
	UNKNOWN("UNKNOWN");

	private String status;

	private ServiceStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static ServiceStatus fromString(String status) {
		for (ServiceStatus s : ServiceStatus.values()) {
			if (s.getStatus().equalsIgnoreCase(status)) {
				return s;
			}
		}
		return UNKNOWN;
	}

}
