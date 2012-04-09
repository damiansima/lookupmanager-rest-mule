package com.mulesfot.lookuptable.persistence.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.mulesfot.lookuptable.persistence.service.ObjectStorePersistenceService;
import com.mulesfot.lookuptable.persistence.service.PersistenceService;

/**
 * This class holds the logic to access the lookup table's data from the data
 * source. Whether it's to Create, Read, Update or Delete it. It also know how
 * properly assemble a key for a lookup table. operate with its data.
 * 
 * @author damiansima
 * 
 */
public class LookUpTableDao {

	private static LookUpTableDao INSTANCE = null;
	
	private static final String KEY_PREFIX = "lookup";
	private static final String SEPARATOR = "_";

	private Map<String, PersistenceService> services = new HashMap<String, PersistenceService>();
	
	private LookUpTableDao() {
	}
	
	public static synchronized LookUpTableDao getInstance(){
		if(INSTANCE == null){
			INSTANCE = new LookUpTableDao();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Return the service for the defined customer.
	 * @param customer
	 * @return
	 */
	private PersistenceService getService(String customer){
		if(this.services.get(customer) == null){
			this.services.put(customer, new ObjectStorePersistenceService(customer));
		}
		
		return this.services.get(customer);
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
	public boolean createLookupTableRecords(String customer,String tableName, String keys, String fields) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");

		String actualKey = this.buildKey(tableName, keys);
		this.getService(customer).createRecords(actualKey, fields);

		return false;
	}

	/**
	 * Retrieves all the records related to a given Lookup Table.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @return
	 */
	public String getLookupTableRecords(String customer,String tableName) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		
		return this.getLookupTableRecords( customer,tableName, "");
	}

	/**
	 * Retrieves all the records related to a given Lookup Table that matches the
	 * provided keys.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @param keys
	 *          the value of the keys
	 * @return
	 */
	public String getLookupTableRecords(String customer,String tableName, String keys) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		
		String actualKey = this.buildKey(tableName, keys);
		this.getService(customer).getLookupRecords(actualKey);

		return null;
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
	public boolean updateLookupTableRecords(String customer,String tableName, String keys, String fields) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		Preconditions.checkArgument(StringUtils.isNotBlank(keys), "The key can not be null nor empty.");
		Preconditions.checkArgument(fields != null, "The field values can not be null.");
		
		String actualKey = this.buildKey(tableName, keys);
		this.getService(customer).updateRecords(actualKey, fields);

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
	public boolean deleteLookupTableRecords(String customer,String tableName) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		
		return this.deleteLookupTableRecords( customer,tableName, "");
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
	public boolean deleteLookupTableRecords(String customer,String tableName, String keys) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		Preconditions.checkArgument(StringUtils.isNotBlank(keys), "The key can not be null nor empty.");
		
		String actualKey = this.buildKey(tableName, keys);
		this.getService(customer).deleteRecords(actualKey);

		return false;
	}

	private String buildKey(String tableName, String key) {
		StringBuffer builder = new StringBuffer();

		builder.append(KEY_PREFIX).append(SEPARATOR);
		builder.append(tableName).append(SEPARATOR);

		if (StringUtils.isNotBlank(key)) {
			builder.append(key);
		}

		return builder.toString();
	}

}
