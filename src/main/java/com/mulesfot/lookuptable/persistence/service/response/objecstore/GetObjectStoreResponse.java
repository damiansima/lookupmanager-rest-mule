package com.mulesfot.lookuptable.persistence.service.response.objecstore;

public class GetObjectStoreResponse {
	private String appId;
	private String store;
	private String key;
	private String value;

	public GetObjectStoreResponse(String appId, String store, String key, String value) {
		super();
		this.appId = appId;
		this.store = store;
		this.key = key;
		this.value = value;
	}

	public String getAppId() {
		return appId;
	}

	public String getStore() {
		return store;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
