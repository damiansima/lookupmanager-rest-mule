package com.mulesfot.lookuptable.persistence.service;

public interface PersistenceService {

	public String createRecords(String key, String values);

	public String getLookupRecords(String key);

	public String updateRecords(String key, String values);

	public String deleteRecords(String key, String values);


}
