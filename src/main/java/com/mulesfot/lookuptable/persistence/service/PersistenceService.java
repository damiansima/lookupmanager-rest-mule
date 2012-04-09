package com.mulesfot.lookuptable.persistence.service;

import java.util.List;

import com.mulesfot.lookuptable.persistence.dao.Response;

public interface PersistenceService {

	public Response createRecords(String key, String values);

	public List<Response> getLookupRecords(String key);

	public Response updateRecords(String key, String values);

	public Response deleteRecords(String key);


}
