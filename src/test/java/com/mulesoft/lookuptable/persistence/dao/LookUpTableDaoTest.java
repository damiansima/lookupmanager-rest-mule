package com.mulesoft.lookuptable.persistence.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.mulesfot.lookuptable.persistence.dao.LookUpTableDao;
import com.mulesfot.lookuptable.persistence.service.PersistenceService;
import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;
import com.sun.jersey.json.impl.provider.entity.JSONArrayProvider;

/**
 * Validates that {@link LookUpTableDao} returns the correct data in the correct
 * format while mocking the actual persistence service.
 * 
 * @author damiansima
 * 
 */
public class LookUpTableDaoTest {
	private static final String CUSTOMER_NAME = "fakecustomer";
	private static final String TABLE_NAME = "faketable";
	private static final String SIMPLE_KEY = "fakesimplekey";
	private static final String COMPOSITE_KEY = "fakecompositekey0|key1|key2";

	private PersistenceService mockPersistenceService = null;

	/**
	 * Push by reflection the Map containing the mock service into the
	 * LookUpTableDao.
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Before
	public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		mockPersistenceService = EasyMock.createMock(PersistenceService.class);

		Map<String, PersistenceService> fakeServicesMap = new HashMap<String, PersistenceService>();
		fakeServicesMap.put(CUSTOMER_NAME, mockPersistenceService);

		Field servicesField = LookUpTableDao.class.getDeclaredField("services");
		servicesField.setAccessible(true);
		servicesField.set(LookUpTableDao.getInstance(), fakeServicesMap);
	}

	@Test
	public void getInstanceTest() {
		LookUpTableDao instance = LookUpTableDao.getInstance();
		Assert.assertNotNull("The LookUpTableDao instance can not be null.", instance);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getLookupTableRecordsByKeyNoTableFailTest() {
		LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, "");
	}

	/**
	 * Validates that the data returned is correct when a simple key is provided.
	 * i.e. a key made up just of one key value.
	 */
	@Test
	public void getLookupTableRecordsByKeyTest() {

		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY, "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		EasyMock.expect(mockPersistenceService.getLookupRecords(EasyMock.anyObject(String.class))).andReturn(responses);

		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY);
		System.out.println(records);

		// Type type = new TypeToken<ArrayList<ArrayList>>(){}.getType();
		// ArrayList<ArrayList> inList = new Gson.fromJson(json, type);
		//
		// for (Object record : recordList) {
		// List rl = (List) record;
		// System.out.println(rl.toString());
		// }

		// TODO make this fails to know where to continue THE JSON IS NOT CORRECT
		// Assert.assertEquals('[["fakesimplekey","field1","field2","fieldn"]]',
		// records);

		EasyMock.verify(mockPersistenceService);
	}

	/**
	 * Validates that the data returned is correct when a composite key is
	 * provided. i.e. a key made up of two keys separated by a
	 * {@link LookUpTableDao.FIELD_SEPARATOR}
	 */
	@Test
	public void getLookupTableRecordsByCompositeKeyTest() {
		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + TABLE_NAME + "_" + COMPOSITE_KEY, "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		EasyMock.expect(mockPersistenceService.getLookupRecords(EasyMock.anyObject(String.class))).andReturn(responses);

		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, COMPOSITE_KEY);
		System.out.println(records);

		EasyMock.verify(mockPersistenceService);
	}

	@Test
	public void getLookupTableRecords() {
		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();

		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY + "1",
				"field1" + LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY + "2",
				"field1" + LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		EasyMock.expect(mockPersistenceService.getLookupRecords(EasyMock.anyObject(String.class))).andReturn(responses);

		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME);
		System.out.println(records);

		EasyMock.verify(mockPersistenceService);
	}

	@Test
	public void createLookupTableRecordsTest() {
		String fakeActualKey = "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY;
		String fakeFields = "field1" + LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR
				+ "fieldn";

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.createRecords(fakeActualKey, fakeFields)).andReturn(fakeResponse);
		EasyMock.replay(mockPersistenceService);

		boolean createSuccess = LookUpTableDao.getInstance().createLookupTableRecords(CUSTOMER_NAME, TABLE_NAME,
				SIMPLE_KEY, fakeFields);

		Assert.assertTrue("The create record operation should have been successful.", createSuccess);

		EasyMock.verify(mockPersistenceService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createLookupTableRecordsNoCustomerFailTest() {
		LookUpTableDao.getInstance().createLookupTableRecords("", TABLE_NAME, SIMPLE_KEY, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createLookupTableRecordsNoTableFailTest() {
		LookUpTableDao.getInstance().createLookupTableRecords(CUSTOMER_NAME, "", SIMPLE_KEY, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createLookupTableRecordsNoKeysFailTest() {
		LookUpTableDao.getInstance().createLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, "", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createLookupTableRecordsNullFieldsFailTest() {
		LookUpTableDao.getInstance().createLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY, null);
	}

	@Test
	public void updateLookupTableRecordsTest() {
		String fakeActualKey = "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY;
		String fakeFields = "field1" + LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR
				+ "fieldn";

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.updateRecords(fakeActualKey, fakeFields)).andReturn(fakeResponse);
		EasyMock.replay(mockPersistenceService);

		boolean createSuccess = LookUpTableDao.getInstance().updateLookupTableRecords(CUSTOMER_NAME, TABLE_NAME,
				SIMPLE_KEY, fakeFields);

		Assert.assertTrue("The update record operation should have been successful.", createSuccess);

		EasyMock.verify(mockPersistenceService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLookupTableRecordsNoCustomerFailTest() {
		LookUpTableDao.getInstance().updateLookupTableRecords("", TABLE_NAME, SIMPLE_KEY, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLookupTableRecordsNoTableFailTest() {
		LookUpTableDao.getInstance().updateLookupTableRecords(CUSTOMER_NAME, "", SIMPLE_KEY, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLookupTableRecordsNoKeysFailTest() {
		LookUpTableDao.getInstance().updateLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, "", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateLookupTableRecordsNullFieldsFailTest() {
		LookUpTableDao.getInstance().updateLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY, null);
	}
	
	@Test
	public void deleteLookupTableRecordsByKeyTest() {
		String fakeActualKey = "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY;

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.deleteRecords(fakeActualKey)).andReturn(fakeResponse);
		EasyMock.replay(mockPersistenceService);

		boolean createSuccess = LookUpTableDao.getInstance()
				.deleteLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY);

		Assert.assertTrue("The delete record operation by key should have been successful.", createSuccess);

		EasyMock.verify(mockPersistenceService);
	}
	
	@Test
	public void deleteLookupTableRecordsTest() {
		String fakeActualKey = "lookup_" + TABLE_NAME + "_" ;

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.deleteRecords(fakeActualKey)).andReturn(fakeResponse);
		EasyMock.replay(mockPersistenceService);

		boolean createSuccess = LookUpTableDao.getInstance()
				.deleteLookupTableRecords(CUSTOMER_NAME, TABLE_NAME);

		Assert.assertTrue("The delete record operation should have been successful.", createSuccess);

		EasyMock.verify(mockPersistenceService);
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void deleteLookupTableRecordsNoCustomerFailTest() {
		LookUpTableDao.getInstance().deleteLookupTableRecords("", TABLE_NAME, SIMPLE_KEY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteLookupTableRecordsNoTableFailTest() {
		LookUpTableDao.getInstance().deleteLookupTableRecords(CUSTOMER_NAME, "", SIMPLE_KEY);
	}

	@Test(expected = NullPointerException.class)
	public void deleteLookupTableRecordsNullKeysFailTest() {
		LookUpTableDao.getInstance().deleteLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, null);
	}


	@After
	public void tearDown() {
		mockPersistenceService = null;
	}

}
