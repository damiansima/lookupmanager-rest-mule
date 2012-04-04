package com.mulesoft.lookuptable.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

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
}