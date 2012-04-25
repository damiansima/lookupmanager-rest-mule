package com.mulesoft.lookuptable.rest.resource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This Class validates the constructions methods for the {@link Field} object.
 * 
 * @author damiansima
 * 
 */
public class FieldTest {
	private static final String NAME = "field_name";
	private static final String VALUE = "field_value";

	private static final String JSON_FIELD = "{\"name\":\"" + NAME + "\",\"value\":\"" + VALUE + "\"}";
	private static final String JSON_FIELD_EMPTY_NAME = "{\"name\":\"\",\"value\":\"" + VALUE + "\"}";
	private static final String JSON_FIELD_EMPTY_VALUE = "{\"name\":\"" + NAME + "\",\"value\":\"\"}";

	@Test
	public void construcitonTest() {
		Field field = new Field(NAME, VALUE);
		Assert.assertNotNull("The field should not be null", field);

		Assert.assertEquals("The name is not the expected", NAME, field.getName());
		Assert.assertEquals("The value is not the expected", VALUE, field.getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void construcitonNullNameTest() {
		new Field(null, VALUE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void construcitonEmptyNameTest() {
		new Field("", VALUE);
	}

	@Test(expected=NullPointerException.class)
	public void construcitonNullValueTest() {
		new Field(NAME, null);
	}

	@Test
	public void toJsonTest() {
		Field field = new Field(NAME, VALUE);

		Assert.assertEquals("The field's JSON representation returned is worng", JSON_FIELD, field.toJson());
	}

	@Test
	public void createFromJsonTest() {
		Field field = Field.createFromJson(JSON_FIELD);
		Assert.assertNotNull("The field should not be null", field);

		Assert.assertEquals("The name is not the expected", NAME, field.getName());
		Assert.assertEquals("The value is not the expected", VALUE, field.getValue());
	}
	
	@Test
	public void createFromJsonEmptyValueTest() {
		Field field = Field.createFromJson(JSON_FIELD_EMPTY_VALUE);
		Assert.assertNotNull("The field should not be null", field);

		Assert.assertEquals("The name is not the expected", NAME, field.getName());
		Assert.assertEquals("The value is not the expected", "", field.getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createFromJsonEmptyNameTest() {
		Key.createFromJson(JSON_FIELD_EMPTY_NAME);
	}
}
