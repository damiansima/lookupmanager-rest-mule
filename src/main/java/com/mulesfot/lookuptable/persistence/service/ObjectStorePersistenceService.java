package com.mulesfot.lookuptable.persistence.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * This class is the REST client of the ObjectStore API.
 * It use Jersey to implements the client.
 * @author damiansima
 *
 */
public class ObjectStorePersistenceService implements PersistenceService {

	private String host = "http://fakehost.com";
	private String baseUrl = "/api/applications";
	private String customer;
	private String trailingUrl = "/objectstore";

	private final Client client;

	public ObjectStorePersistenceService(String customer) {
		this.customer = customer;
		this.client = Client.create();
	}

	public String createRecords(String key, String values) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String response = webResource.put(String.class);

		return response;
	}

	public String getLookupRecords(String key) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String response = webResource.get(String.class);

		return response;
	}

	public String updateRecords(String key, String values) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String response = webResource.put(String.class);

		return response;
	}

	public String deleteRecords(String key) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String response = webResource.delete(String.class);

		return response;
	}

	private String buildBaseUrl() {
		return this.host + this.baseUrl + "/" + this.customer + this.trailingUrl;
	}

}
