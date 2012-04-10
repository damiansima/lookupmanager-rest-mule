package com.mulesoft.lookuptable.persistence.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mulesfot.lookuptable.persistence.dao.LookUpTableDao;
import com.mulesfot.lookuptable.persistence.service.PersistenceService;
import com.mulesfot.lookuptable.persistence.service.PersistenceServiceResponse;

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

	@Test
	public void getLookupTableRecordsByKeyTest() {

		List<PersistenceServiceResponse> responses = new ArrayList<PersistenceServiceResponse>();
		responses.add(new PersistenceServiceResponse(true, 200, "", "lookup_" + TABLE_NAME + "_" + SIMPLE_KEY, "field1"
				+ LookUpTableDao.FIELD_SEPARATOR + "field2" + LookUpTableDao.FIELD_SEPARATOR + "fieldn"));
		EasyMock.expect(mockPersistenceService.getLookupRecords(EasyMock.anyObject(String.class))).andReturn(responses);

		EasyMock.replay(mockPersistenceService);

		String records = LookUpTableDao.getInstance().getLookupTableRecords(CUSTOMER_NAME, TABLE_NAME, SIMPLE_KEY);

		System.out.println(records);
		
		//TODO make this fails to know where to continue THE JSON IS NOT CORRECT
		Assert.assertTrue(false);

		EasyMock.verify(mockPersistenceService);
	}

	@After
	public void tearDown() {
		mockPersistenceService = null;
	}

}
