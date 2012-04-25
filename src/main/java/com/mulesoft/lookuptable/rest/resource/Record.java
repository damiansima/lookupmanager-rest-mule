package com.mulesoft.lookuptable.rest.resource;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

/**
 * This class represents a filed that would be received as an input or returned
 * from a Jersey resource.
 * 
 * @author damiansima
 * 
 */
public class Record {
	private List<Field> keys;
	private List<Field> values;
	
	public static Record createFromJson(String jsonRecord){
		return new Gson().fromJson(jsonRecord, Record.class);
	}

	public Record(List<Field> keys, List<Field> values) {
		Preconditions.checkNotNull(keys,"The keys of a record can not be null");
		Preconditions.checkArgument(!keys.isEmpty(),"The keys of a record can not be empty");
		
		Preconditions.checkNotNull(values,"The values of a record can not be null");
		Preconditions.checkArgument(!values.isEmpty(),"The values of a record can not be empty");
		
		this.keys = keys;
		this.values = values;
	}

	public List<Field> getKeys() {
		return keys;
	}

	public List<Field> getValues() {
		return values;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

}
