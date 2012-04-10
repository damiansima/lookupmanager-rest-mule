package com.mulesfot.lookuptable.persistence.service;

import java.util.ArrayList;
import java.util.List;

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

	public PersistenceServiceResponse createRecords(String key, String values) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String serviceResponse = webResource.put(String.class);
		PersistenceServiceResponse response = null;
		
		return response;
	}

	public List<PersistenceServiceResponse> getLookupRecords(String key) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());
		

		String serviceResponse = webResource.get(String.class);
		PersistenceServiceResponse response = null;
		
		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		
		return responses;
	}

	public PersistenceServiceResponse updateRecords(String key, String values) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String serviceResponse = webResource.put(String.class);
		PersistenceServiceResponse response = null;
		
		return response;
	}

	public PersistenceServiceResponse deleteRecords(String key) {
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		String serviceResponse = webResource.delete(String.class);
		PersistenceServiceResponse response = null;
		
		return response;
	}

	private String buildBaseUrl() {
		return this.host + this.baseUrl + "/" + this.customer + this.trailingUrl;
	}

}
