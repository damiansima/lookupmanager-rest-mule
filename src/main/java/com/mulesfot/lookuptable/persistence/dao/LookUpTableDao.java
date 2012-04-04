package com.mulesfot.lookuptable.persistence.dao;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
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
	private static final String KEY_PREFIX = "lookup";
	private static final String SEPARATOR = "_";
	
	private PersistenceService persistenceService;
	
	

	public LookUpTableDao(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
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
	public boolean createLookupTableRecords(String tableName, String keys, String fields) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");

		String actualKey = this.buildKey(tableName, keys);

		return false;
	}

	/**
	 * Retrieves all the records related to a given Lookup Table.
	 * 
	 * @param tableName
	 *          the name of the lookup table
	 * @return
	 */
	public String getLookupTableRecords(String tableName) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		
		return this.getLookupTableRecords(tableName, "");
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
	public String getLookupTableRecords(String tableName, String keys) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		
		String actualKey = this.buildKey(tableName, keys);

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
	public boolean updateLookupTableRecords(String tableName, String keys, String fields) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		Preconditions.checkArgument(StringUtils.isNotBlank(keys), "The key can not be null nor empty.");
		Preconditions.checkArgument(fields != null, "The field values can not be null.");
		
		String actualKey = this.buildKey(tableName, keys);

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
	public boolean deleteLookupTableRecords(String tableName) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		
		return this.deleteLookupTableRecords(tableName, "");
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
	public boolean deleteLookupTableRecords(String tableName, String keys) {
		Preconditions.checkArgument(StringUtils.isNotBlank(tableName), "The lookup table name can not be null nor empty.");
		Preconditions.checkArgument(StringUtils.isNotBlank(keys), "The key can not be null nor empty.");
		
		String actualKey = this.buildKey(tableName, keys);

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
