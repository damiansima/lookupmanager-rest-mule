package com.mulesoft.lookuptable.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecordTest {
	private static final String KEY_NAME = "key_name";
	private static final String KEY_VALUE = "key_value";

	private static final String FIELD_NAME = "field_name";
	private static final String FIELD_VALUE = "field_value";

	private static final String JSON_RECORD = "{\"keys\":[{\"name\":\"" + KEY_NAME + "\",\"value\":\"" + KEY_VALUE
			+ "\"}],\"values\":[{\"name\":\"" + FIELD_NAME + "\",\"value\":\"" + FIELD_VALUE + "\"}]}";

	private static List<Field> KEYS;
	private static List<Field> VALUES;

	@Before
	public void setUp() {
		KEYS = new ArrayList<Field>();
		KEYS.add(new Field(KEY_NAME, KEY_VALUE));

		VALUES = new ArrayList<Field>();
		VALUES.add(new Field(FIELD_NAME, FIELD_VALUE));
	}

	@Test
	public void construcitonTest() {
		Record record = new Record(KEYS, VALUES);
		Assert.assertNotNull("The record should not be null", record);

		Assert.assertEquals("The keys are not the expected", KEYS, record.getKeys());
		Assert.assertEquals("The values are not the expected", VALUES, record.getValues());
	}

	@Test(expected = NullPointerException.class)
	public void construcitonNullKeysTest() {
		new Record(null, VALUES);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construcitonEmptyKeysTest() {
		new Record(new ArrayList<Field>(), VALUES);
	}

	@Test(expected = NullPointerException.class)
	public void construcitonNullValiuesTest() {
		new Record(KEYS, null);
	}

	@Test
	public void toJsonTest() {
		Record record = new Record(KEYS, VALUES);

		Assert.assertEquals("The record's JSON representation returned is worng", JSON_RECORD, record.toJson());
	}

	@Test
	public void createFromJsonTest() {
		Record record = Record.createFromJson(JSON_RECORD);
		Assert.assertNotNull("The record should not be null", record);

		Assert.assertEquals("The keys are not the expected", KEYS, record.getKeys());
		Assert.assertEquals("The values are not the expected", VALUES, record.getValues());
	}

}
