package com.mulesoft.lookuptable.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CustomWebApplicationException extends WebApplicationException {
	public CustomWebApplicationException(Response.Status status, String message) {
		super(Response.status(status).entity(message).build());

	}
}