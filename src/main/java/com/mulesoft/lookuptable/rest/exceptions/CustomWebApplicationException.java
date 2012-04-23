package com.mulesoft.lookuptable.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.mulesoft.lookuptable.rest.response.LookupManagerResponse;

/**
 * This Excepting extends from {@link WebApplicationException}. The idea is to
 * have an exception that can throws different HTTP error code with a custom
 * message.
 * 
 * @TODO define different constructors to receive JSON error messages.
 * 
 * @author damiansima
 * 
 */
public class CustomWebApplicationException extends WebApplicationException {
	
	public CustomWebApplicationException(Response.Status status, String message) {
		super(Response.status(status).entity(message).build());
	}
	
	public CustomWebApplicationException(LookupManagerResponse response) {
		super(Response.status(response.getHttpStatus()).entity(response.getErrorMessage() + "-" + response.getData()).build());
	}

	
}