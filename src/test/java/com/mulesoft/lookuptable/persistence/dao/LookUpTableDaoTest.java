package com.mulesoft.lookuptable.persistence.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mulesfot.lookuptable.persistence.dao.LookUpTableDao;
import com.mulesfot.lookuptable.persistence.service.PersistenceService;
import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;

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
		ArrayList<String> expectedRecord = new ArrayList<String>();
		expectedRecord.add(SIMPLE_KEY);
		expectedRecord.add("field1");
		expectedRecord.add("field2");
		expectedRecord.add("fieldn");

		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + SIMPLE_KEY, "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		EasyMock.expect(mockPersistenceService.getRecords(EasyMock.anyObject(String.class))).andReturn(responses);
		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY);

		Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
		}.getType();
		ArrayList<ArrayList<String>> recordList = new Gson().fromJson(records, type);

		Assert.assertEquals("The amount or records returned is wrong", 1, recordList.size());

		for (ArrayList<String> record : recordList) {
			Assert.assertEquals("The record returned is wrong", expectedRecord, record);
		}

		EasyMock.verify(mockPersistenceService);
	}

	/**
	 * Validates that the data returned is correct when a composite key is
	 * provided. i.e. a key made up of two keys separated by a
	 * {@link LookUpTableDao.FIELD_SEPARATOR}
	 */
	@Test
	public void getLookupTableRecordsByCompositeKeyTest() {
		ArrayList<String> expectedRecord = new ArrayList<String>();
		expectedRecord.add("fakecompositekey0");
		expectedRecord.add("key1");
		expectedRecord.add("key2");
		expectedRecord.add("field1");
		expectedRecord.add("field2");
		expectedRecord.add("fieldn");
		
		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + COMPOSITE_KEY, "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		EasyMock.expect(mockPersistenceService.getRecords(EasyMock.anyObject(String.class))).andReturn(responses);

		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, COMPOSITE_KEY);

		Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
		}.getType();
		ArrayList<ArrayList<String>> recordList = new Gson().fromJson(records, type);

		Assert.assertEquals("The amount or records returned is wrong", 1, recordList.size());

		for (ArrayList<String> record : recordList) {
			Assert.assertEquals("The record returned is wrong", expectedRecord, record);
		}

		EasyMock.verify(mockPersistenceService);
	}

	@Test
	public void getLookupTableRecords() {
		ArrayList<String> expectedRecord_1 = new ArrayList<String>();
		expectedRecord_1.add(SIMPLE_KEY + "1");
		expectedRecord_1.add("field1");
		expectedRecord_1.add("field2");
		expectedRecord_1.add("fieldn");
		
		
		ArrayList<String> expectedRecord_2 = new ArrayList<String>();
		expectedRecord_2.add(SIMPLE_KEY + "2");
		expectedRecord_2.add("field1");
		expectedRecord_2.add("field2");
		expectedRecord_2.add("fieldn");
		
		ArrayList<ArrayList<String>> expectedRecordList = new ArrayList<ArrayList<String>>();
		expectedRecordList.add(expectedRecord_1);
		expectedRecordList.add(expectedRecord_2);
		
		
		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + SIMPLE_KEY + "1", "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + SIMPLE_KEY + "2", "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));

		EasyMock.expect(mockPersistenceService.getRecords(EasyMock.anyObject(String.class))).andReturn(responses);

		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME);

		Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
		}.getType();
		ArrayList<ArrayList<String>> recordList = new Gson().fromJson(records, type);

		Assert.assertEquals("The amount or records returned is wrong", expectedRecordList.size(), recordList.size());
		Assert.assertEquals("The record list returned is wrong", expectedRecordList, recordList);
		
		for (ArrayList<String> record : recordList) {
			Assert.assertTrue("The Record: "+ record.toString() + " is not present in the expected record list",expectedRecordList.contains(record));
		}
		
		
		EasyMock.verify(mockPersistenceService);
	}

	@Test
	public void createLookupTableRecordsTest() {
		String fakeActualKey = "lookup_" + SIMPLE_KEY;
		String fakeFields = "field1" + LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR
				+ "fieldn";

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.createRecords(TABLE_NAME, fakeActualKey, fakeFields))
				.andReturn(fakeResponse);
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
		String fakeActualKey = "lookup_" + SIMPLE_KEY;
		String fakeFields = "field1" + LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR
				+ "fieldn";

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.updateRecords(TABLE_NAME, fakeActualKey, fakeFields))
				.andReturn(fakeResponse);
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
		String fakeActualKey = "lookup_" + SIMPLE_KEY;

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.deleteRecord(TABLE_NAME, fakeActualKey)).andReturn(fakeResponse);
		EasyMock.replay(mockPersistenceService);

		boolean createSuccess = LookUpTableDao.getInstance()
				.deleteLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY);

		Assert.assertTrue("The delete record operation by key should have been successful.", createSuccess);

		EasyMock.verify(mockPersistenceService);
	}

	@Test
	public void deleteLookupTableRecordsTest() {

		PersistenceServiceResponse fakeResponse = new PersistenceServiceResponse(true, 200, "", "", "");
		EasyMock.expect(mockPersistenceService.deleteRecords(TABLE_NAME)).andReturn(fakeResponse);
		EasyMock.replay(mockPersistenceService);

		boolean createSuccess = LookUpTableDao.getInstance().deleteLookupTableRecords(CUSTOMER_NAME, TABLE_NAME);

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
