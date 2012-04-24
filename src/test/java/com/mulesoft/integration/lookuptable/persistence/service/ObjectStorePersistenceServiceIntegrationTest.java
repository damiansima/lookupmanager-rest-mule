package com.mulesoft.integration.lookuptable.persistence.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.mulesfot.lookuptable.persistence.service.ObjectStorePersistenceService;
import com.mulesfot.lookuptable.persistence.service.PersistenceService;
import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;

public class ObjectStorePersistenceServiceIntegrationTest {

	private static String FAKE_CUSTOMER = "damianFakeCustomer";
	private static String FAKE_TABLE_NAME = "lookup_faketablename";
	private static final String SIMPLE_KEY = "fakesimplekey";
	private static final String COMPOSITE_KEY = "fakecompositekey0|key1|key2";

	private static final String FAKE_FIELDS = "field0|field1|field2";

	@Test
	public void createRecords() {
		PersistenceService service = new ObjectStorePersistenceService(FAKE_CUSTOMER);
		PersistenceServiceResponse response = service.createRecords(FAKE_TABLE_NAME, SIMPLE_KEY, FAKE_FIELDS);
		Assert.assertEquals("The response code should have been 201", 201, response.getServiceStatus());

	}

	@Test
	public void getLookupRecord() {
		PersistenceService service = new ObjectStorePersistenceService(FAKE_CUSTOMER);
		PersistenceServiceResponse response = service.getRecord(FAKE_TABLE_NAME, SIMPLE_KEY);

		Assert.assertEquals("The response code should have been 200", 200, response.getServiceStatus());
		Assert.assertEquals(SIMPLE_KEY, response.getKey());
		Assert.assertEquals(FAKE_FIELDS, response.getValue());
	}

	@Test
	public void getLookupRecords() {
		PersistenceService service = new ObjectStorePersistenceService(FAKE_CUSTOMER);

		List<PersistenceServiceResponse> responses = service.getRecords(FAKE_TABLE_NAME);
		for(PersistenceServiceResponse response: responses){
			Assert.assertEquals("The response code should have been 200", 200, response.getServiceStatus());
		}
	}

	@Test
	public void deleteLookupRecord() {
		PersistenceService service = new ObjectStorePersistenceService(FAKE_CUSTOMER);

		PersistenceServiceResponse response = service.deleteRecord(FAKE_TABLE_NAME, SIMPLE_KEY);
		Assert.assertEquals("The response code should have been 204", 204, response.getServiceStatus());
	}

	@Test
	public void deleteLookupRecords() {
		PersistenceService service = new ObjectStorePersistenceService(FAKE_CUSTOMER);

		service.createRecords(FAKE_TABLE_NAME, SIMPLE_KEY, FAKE_FIELDS);
		PersistenceServiceResponse response = service.deleteRecord(FAKE_TABLE_NAME, SIMPLE_KEY);
		Assert.assertEquals("The response code should have been 204", 204, response.getServiceStatus());
	}
}
