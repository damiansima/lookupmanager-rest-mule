package com.mulesoft.lookuptable.rest.resource;

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
public class Field {
	private String name;
	private String value;

	public static Field createFromJson(String jsonField){
		return new Gson().fromJson(jsonField, Field.class);
	}

	public Field(String name, String value) {
		Preconditions.checkArgument(StringUtils.isNotBlank(name),"The name of the field can not be null nor empty.");
		Preconditions.checkNotNull(value,"The value of a field can not be null");

		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
