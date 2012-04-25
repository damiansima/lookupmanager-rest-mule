package com.mulesoft.lookuptable.rest.resource;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

/**
 * A {@link Key} is a {@link Field} that doesn't allow empty value.
 * 
 * @author damiansima
 * 
 */
public class Key extends Field {
	private static void validateNameAndValue(String name, String value){
		Preconditions.checkArgument(StringUtils.isNotBlank(name), "The name of the field can not be null nor empty.");
		Preconditions.checkArgument(StringUtils.isNotBlank(value), "The value of a key can not be null nor empty");
	}
	
	public static Key createFromJson(String jsonKey) {
		Key newKey =  new Gson().fromJson(jsonKey, Key.class);
		
		validateNameAndValue(newKey.getName(), newKey.getValue());
		return newKey;
	}

	public Key(String name, String value) {
		validateNameAndValue(name, value);
		this.name = name;
		this.value = value;
	}

}
