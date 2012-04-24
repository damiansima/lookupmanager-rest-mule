package com.mulesfot.lookuptable.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;
import com.mulesfot.lookuptable.persistence.service.response.objecstore.GetAllObjectStoreResponse;
import com.mulesfot.lookuptable.persistence.service.response.objecstore.GetAllObjectStoreResponse.GetAllItem;
import com.mulesfot.lookuptable.persistence.service.response.objecstore.GetObjectStoreResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * This class is the REST client of the ObjectStore API. It use Jersey to
 * implements the client.
 * 
 * @author damiansima
 * 
 */
public class ObjectStorePersistenceService implements PersistenceService {

	private String host = "https://object-store-QA-1610374657.us-east-1.elb.amazonaws.com";
	private String baseUrl = "/apps";
	private String appId;

	private Client client;

	private String buildBaseUrl() {
		return this.host + this.baseUrl + "/" + this.appId;
	}

	public ObjectStorePersistenceService(String appId) {
		this.appId = appId;
		this.client = Client.create();

		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				// System.out.println("Warning: URL Host: " + hostname + " vs. " +
				// session.getPeerHost());
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	@SuppressWarnings("unchecked")
	public PersistenceServiceResponse createRecords(String tableName, String key, String values) {
		String url = this.buildBaseUrl() + "/" + tableName + "/" + key;
		WebResource webResource = this.client.resource(url);

		PersistenceServiceResponse response;
		try {
			String jsonValue = "{\"value\":\"" + values + "\"}";
			webResource.type("application/json").put(String.class, jsonValue);

			response = new PersistenceServiceResponse(true, 201);
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			response = new PersistenceServiceResponse(true, 409);
		}

		return response;
	}

	public PersistenceServiceResponse getRecord(String tableName, String key) {

		String url = this.buildBaseUrl() + "/" + tableName + "/" + key;
		WebResource webResource = this.client.resource(url);

		PersistenceServiceResponse response;
		try {
			String serviceResponse = webResource.type("application/json").get(String.class);
			GetObjectStoreResponse actualServiceResponse = new Gson().fromJson(serviceResponse, GetObjectStoreResponse.class);

			response = new PersistenceServiceResponse(true, 200, "", actualServiceResponse.getKey(),
					actualServiceResponse.getValue());
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			response = new PersistenceServiceResponse(true, 404);
		}

		return response;
	}

	public List<PersistenceServiceResponse> getRecords(String tableName) {
		String url = this.buildBaseUrl() + "/" + tableName;
		WebResource webResource = this.client.resource(url);

		ArrayList<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		try {
			String serviceResponse = webResource.type("application/json").get(String.class);

			GetAllObjectStoreResponse actualServiceResponse = new Gson().fromJson(serviceResponse,
					GetAllObjectStoreResponse.class);

			for (GetAllItem item : actualServiceResponse.getData()) {
				PersistenceServiceResponse response = new PersistenceServiceResponse(true, 200, "", item.getKey(),
						item.getValue());
				responses.add(response);
			}

		} catch (UniformInterfaceException e) {
			e.printStackTrace();
			responses.add(new PersistenceServiceResponse(true, 404));
		}

		return responses;
	}

	/**
	 * In the ObjectStore persistence service the update has been implemented as a
	 * create.
	 */
	public PersistenceServiceResponse updateRecords(String tableName, String key, String values) {

		return this.createRecords(tableName, key, values);
	}

	public PersistenceServiceResponse deleteRecord(String tableName, String key) {
		String url = this.buildBaseUrl() + "/" + tableName + "/" + key;
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		PersistenceServiceResponse response;
		try {
			String r = webResource.type("application/json").delete(String.class);
			System.out.println(r);
			response = new PersistenceServiceResponse(true, 204);
		} catch (UniformInterfaceException e) {
			response = new PersistenceServiceResponse(true, 404);
		}

		return response;
	}

	public PersistenceServiceResponse deleteRecords(String tableName) {
		String url = this.buildBaseUrl() + "/" + tableName;
		WebResource webResource = this.client.resource(this.buildBaseUrl());

		PersistenceServiceResponse response;
		try {
			String r = webResource.type("application/json").delete(String.class);
			System.out.println(r);
			response = new PersistenceServiceResponse(true, 204);
		} catch (UniformInterfaceException e) {
			response = new PersistenceServiceResponse(true, 404);
		}

		return response;
	}
}
