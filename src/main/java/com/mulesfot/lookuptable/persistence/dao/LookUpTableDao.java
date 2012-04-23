package com.mulesfot.lookuptable.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.mulesfot.lookuptable.persistence.service.ObjectStorePersistenceService;
import com.mulesfot.lookuptable.persistence.service.PersistenceService;
import com.mulesfot.lookuptable.persistence.service.response.PersistenceServiceResponse;

/**
 * This class holds the logic to access the lookup table's data from the data
 * source. Whether it's to Create, Read, Update or Delete it. It also knows how
 * properly assemble a key for a lookup table to operate with its data.
 * 
 * @author damiansima
 * 
 */
public class LookUpTableDao {

	private static LookUpTableDao INSTANCE = null;

	private static final String KEY_PREFIX = "lookup";
	private static final String SEPARATOR = "_";

	public static final String FIELD_SEPARATOR = "|";

	private static final String ESCAPED_FIELD_SEPARATOR = "\\" + FIELD_SEPARATOR;

	private Map<String, PersistenceService> services = new HashMap<String, PersistenceService>();

	private LookUpTableDao() {
	}

	/**
	 * Return the service for the defined customer.
	 * 
	 * @param customer
	 * @return the default a PersistenceService instance 
	 */
	private PersistenceService getService(String customer) {
		if (this.services.get(customer) == null) {
			this.services.put(customer, new ObjectStorePersistenceService(customer));
		}

		return this.services.get(customer);
	}

	/**
	 * Creates the key that will be store. It prefix the key with the custom value
	 * and with some identifier.
	 * 
	 * @param tableName
	 * @param key
	 * @return
	 */
	private String buildKey(String tableName, String key) {
		StringBuffer builder = new StringBuffer();

		builder.append(KEY_PREFIX).append(SEPARATOR);
		builder.append(tableName).append(SEPARATOR);

		if (StringUtils.isNotBlank(key)) {
			builder.append(key);
		}

		return builder.toString();
	}

	/**
	 * Parse the persisted key to obtain the relevant data useful for the client.
	 * 
	 * @param actualKey
	 * @return
	 */
	private String getKey(String actualKey) {
		return actualKey.split(SEPARATOR)[2];
	}

	private void validateCustomerAndTableName(String customer, String tableName) {
		Preconditions.checkArgument(StringUtils.isNotBlank(customer), "The customer table can not be null nor empty.");
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
	}

	public static synchronized LookUpTableDao getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LookUpTableDao();
		}

		return INSTANCE;
	}

	/**
	 * Saves the record related in the given Lookup Table.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @param keys
	 *          the value of the keys
	 * @param fields
	 *          the value of the fields
	 * @return false if the key already exits, or if there is an issue with the
	 *         persistence service.
	 */
	public boolean createLookupTableRecords(String customer, String tableName, String keys, String fields) {
		validateCustomerAndTableName(customer, tableName);

		Preconditions.checkArgument(StringUtils.isNotBlank(keys), "The keys can not be null nor empty.");
		Preconditions.checkArgument(fields != null, "The field values can not be null.");

		String actualKey = this.buildKey(tableName, keys);
		PersistenceServiceResponse serviceResponse = this.getService(customer).createRecords(actualKey, fields);

		if (serviceResponse.isSuccesfull()) {
			return true;
		}

		return false;
	}

	/**
	 * Retrieves all the records related to a given Lookup Table.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @return empty JSon String list if no data was found
	 */
	public String getLookupTableRecords(String customer, String tableName) {
		validateCustomerAndTableName(customer, tableName);

		return this.getLookupTableRecords(customer, tableName, "");
	}

	/**
	 * Retrieves all the records related to a given Lookup Table that matches the
	 * provided keys.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @param keys
	 *          the value of the keys
	 * @return empty JSon String list if no data was found
	 */
	public String getLookupTableRecords(String customer, String tableName, String keys) {
		validateCustomerAndTableName(customer, tableName);

		String actualKey = this.buildKey(tableName, keys);
		List<PersistenceServiceResponse> responses = this.getService(customer).getLookupRecords(actualKey);

		List<List> records = new ArrayList<List>();
		for (PersistenceServiceResponse response : responses) {
			List<String> record = new ArrayList<String>();

			for (String k : this.getKey(response.getKey()).split(ESCAPED_FIELD_SEPARATOR)) {
				record.add(k);
			}

			for (String f : response.getValue().split(ESCAPED_FIELD_SEPARATOR)) {
				record.add(f);
			}

			records.add(record);
		}

		return new Gson().toJson(records);
	}

	/**
	 * Updates the record data in the given Lookup Table.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @param keys
	 *          the value of the keys
	 * @param fields
	 *          the value of the fields
	 * @return false if the key didn't exits, or if there is an issue with the
	 *         persistence service.
	 */
	public boolean updateLookupTableRecords(String customer, String tableName, String keys, String fields) {

		validateCustomerAndTableName(customer, tableName);
		Preconditions.checkArgument(StringUtils.isNotBlank(keys), "The key can not be null nor empty.");
		Preconditions.checkArgument(fields != null, "The field values can not be null.");

		String actualKey = this.buildKey(tableName, keys);
		PersistenceServiceResponse serviceResponse = this.getService(customer).updateRecords(actualKey, fields);

		if (serviceResponse.isSuccesfull()) {
			return true;
		}

		return false;
	}

	/**
	 * Deletes all the records related to a given Lookup Table.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @return false if the lookup table didn't exits, or if there is an issue
	 *         with the persistence service
	 */
	public boolean deleteLookupTableRecords(String customer, String tableName) {
		return this.deleteLookupTableRecords(customer, tableName, "");
	}

	/**
	 * Deletes all the records related to a given Lookup Table that matches the
	 * provided keys.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @param keys
	 *          the value of the keys
	 * @return false if the key didn't exits, or if there is an issue with the
	 *         persistence service
	 */
	public boolean deleteLookupTableRecords(String customer, String tableName, String keys) {
		validateCustomerAndTableName(customer, tableName);

		Preconditions.checkNotNull(keys, "The key can not be null.");

		String actualKey = this.buildKey(tableName, keys);
		PersistenceServiceResponse serviceResponse = this.getService(customer).deleteRecords(actualKey);

		if (serviceResponse.isSuccesfull()) {
			return true;
		}

		return false;
	}

}
