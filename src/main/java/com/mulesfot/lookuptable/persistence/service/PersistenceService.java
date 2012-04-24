package com.mulesfot.lookuptable.persistence.service;

import java.util.List;

import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;


public interface PersistenceService {

	public PersistenceServiceResponse createRecords(String tableName, String key, String values);

	public PersistenceServiceResponse getRecord(String tableName, String key);
	
	public List<PersistenceServiceResponse> getRecords(String tableName);
	
	public PersistenceServiceResponse updateRecords(String tableName, String key, String values);

	public PersistenceServiceResponse deleteRecord(String tableName, String key);
	
	public PersistenceServiceResponse deleteRecords(String tableName);


}
