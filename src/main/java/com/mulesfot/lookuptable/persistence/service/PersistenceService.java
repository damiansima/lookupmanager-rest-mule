package com.mulesfot.lookuptable.persistence.service;

import java.util.List;

import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;


public interface PersistenceService {

	public PersistenceServiceResponse createRecords(String key, String values);

	public List<PersistenceServiceResponse> getLookupRecords(String key);

	public PersistenceServiceResponse updateRecords(String key, String values);

	public PersistenceServiceResponse deleteRecords(String key);


}
