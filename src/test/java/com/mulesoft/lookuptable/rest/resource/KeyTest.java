package com.mulesoft.lookuptable.rest.resource;

import org.junit.Assert;
import org.junit.Test;

/**
 * This Class validates the constructions methods for the {@link Field} object.
 * 
 * @author damiansima
 * 
 */
public class KeyTest {
	private static final String NAME = "field_name";
	private static final String VALUE = "field_value";

	private static final String JSON_KEY = "{\"name\":\"" + NAME + "\",\"value\":\"" + VALUE + "\"}";
	private static final String JSON_KEY_EMPTY_NAME = "{\"name\":\"\",\"value\":\"" + VALUE + "\"}";
	private static final String JSON_KEY_EMPTY_VALUE = "{\"name\":\"" + NAME + "\",\"value\":\"\"}";

	@Test
	public void construcitonTest() {
		Key field = new Key(NAME, VALUE);
		Assert.assertNotNull("The field should not be null", field);

		Assert.assertEquals("The name is not the expected", NAME, field.getName());
		Assert.assertEquals("The value is not the expected", VALUE, field.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construcitonNullNameTest() {
		new Key(null, VALUE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construcitonEmptyNameTest() {
		new Key("", VALUE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construcitonNullValueTest() {
		new Key(NAME, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construcitonEmptyValueTest() {
		new Key(NAME, "");
	}

	@Test
	public void toJsonTest() {
		Key field = new Key(NAME, VALUE);

		Assert.assertEquals("The field's JSON representation returned is worng", JSON_KEY, field.toJson());
	}

	@Test
	public void createFromJsonTest() {
		Key field = Key.createFromJson(JSON_KEY);
		Assert.assertNotNull("The field should not be null", field);

		Assert.assertEquals("The name is not the expected", NAME, field.getName());
		Assert.assertEquals("The value is not the expected", VALUE, field.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void createFromJsonEmptyNameTest() {
		Key.createFromJson(JSON_KEY_EMPTY_NAME);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createFromJsonEmptyValueTest() {
		Key.createFromJson(JSON_KEY_EMPTY_VALUE);
	}
}
