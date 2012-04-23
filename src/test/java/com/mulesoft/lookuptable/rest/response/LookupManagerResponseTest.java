package com.mulesoft.lookuptable.rest.response;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.mulesoft.lookuptable.rest.response.LookupManagerResponse;

public class LookupManagerResponseTest {

	@Test
	public void constructionTest() {
		Assert.assertNotNull("The resonse should not be null", new LookupManagerResponse(
				LookupManagerResponse.HttpStatus.OK, "", ""));
	}

	@Test(expected = NullPointerException.class)
	public void constructionNullStatusFailTest() {
		new LookupManagerResponse(null, "", "");
	}

	@Test
	public void toJsonTest() {
		LookupManagerResponse response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.OK, "", "");

		LookupManagerResponse responseFromJson = new Gson().fromJson(response.toJson(), LookupManagerResponse.class);

		Assert.assertEquals("The serialization and de serialization JSon process failed.", response.toJson(),
				responseFromJson.toJson());
	}
}
