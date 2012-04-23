package com.mulesoft.lookuptable.rest.response;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

public class LookupManagerResponse {
	public enum HttpStatus {
		OK(200), BAD_REQUEST(400), INTERNAL_SERVER_ERROR(500), NOT_IMPLEMENTED(501), SERVICE_UNAVAILABLE(503);

		private int status;

		private HttpStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return this.status;
		}
	}

	private int httpStatus;
	private String errorMessage = "";
	private String data;

	public LookupManagerResponse(HttpStatus httpStatus, String errorMessage, String data) {
		super();

		Preconditions.checkNotNull(httpStatus, "You must provide an HttpStatus.");

		this.httpStatus = httpStatus.getStatus();
		this.errorMessage = errorMessage;
		this.data = data;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getData() {
		return data;
	}

	/**
	 * Returns the JSON representation of the object.
	 * 
	 * @return
	 */
	public String toJson() {
		return new Gson().toJson(this);
	}

}
