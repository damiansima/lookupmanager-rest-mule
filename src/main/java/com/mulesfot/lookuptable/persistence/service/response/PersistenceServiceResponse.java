package com.mulesfot.lookuptable.persistence.service.response;

import com.google.common.base.Preconditions;

/**
 * Represents a custom response structure from any Persistence Service.
 * 
 * @author damiansima
 * 
 */
public class PersistenceServiceResponse {
	private boolean succesfull;

	private int serviceStatus;
	private String serviceMessage;

	private String key;
	private String value;

	public PersistenceServiceResponse(boolean succesfull, int serviceStatus) {
		this(succesfull, serviceStatus, "", "", "");
	}

	public PersistenceServiceResponse(boolean succesfull, int serviceStatus, String serviceMessage, String key,
			String value) {
		super();

		this.succesfull = succesfull;
		this.serviceStatus = serviceStatus;
		this.serviceMessage = serviceMessage;
		this.key = key;
		this.value = value;
	}

	public boolean isSuccesfull() {
		return succesfull;
	}

	public int getServiceStatus() {
		return serviceStatus;
	}

	public String getServiceMessage() {
		return serviceMessage;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
