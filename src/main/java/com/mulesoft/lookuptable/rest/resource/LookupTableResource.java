package com.mulesoft.lookuptable.rest.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.mulesfot.lookuptable.persistence.dao.LookUpTableDao;
import com.mulesoft.lookuptable.rest.exceptions.CustomWebApplicationException;
import com.mulesoft.lookuptable.rest.response.LookupManagerResponse;

/**
 * This class represent the LookUp Table Resource. This resource expose an
 * interface for CRUD operations over any lookup table provided.
 * 
 * It acts as a facade for the actual persistence layer.
 * 
 * @author damiansima
 * 
 */
@Path("/customer/{customer}/lookuptables")
public class LookupTableResource {

	/**
	 * This method should persist the record send as a parameter.
	 * 
	 * URL request: POST
	 * /lookuptables/table_name?keys=value1,valueN&fields=value1,valueN
	 * 
	 * @param tableName
	 *          the lookup table where the data belongs to.
	 * @param keys
	 *          the value/s for the key field/s
	 * @param fields
	 *          the value/s the regular field/s
	 * @return 200 HTTP Status or 500 if something failed
	 */
	@POST
	@Produces("application/json")
	@Path("/{tablename}")
	public String createData(@PathParam("customer") String customer, @PathParam("tablename") String tableName,
			@QueryParam("keys") String keys, @QueryParam("fields") String fields) {

		if (keys == null || fields == null) {
			throw new CustomWebApplicationException(Response.Status.BAD_REQUEST,
					"Can not create a record whit out providing keys and fields");
		}

		boolean sucess = LookUpTableDao.getInstance().createLookupTableRecords(customer, tableName, keys, fields);

		LookupManagerResponse response;
		if (sucess) {
			response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.OK, "Data Creation sucessful", "");
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("LookUpTable: ").append(tableName);
			builder.append("|keys: ").append(keys);
			builder.append("|fields: ").append(fields);
			builder.append("|customer: ").append(customer);

			response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.INTERNAL_SERVER_ERROR,
					"There has been an error with the data creation", builder.toString());
			throw new CustomWebApplicationException(response);
		}

		return response.toJson();
	}

	/**
	 * It should return a listing for the requested records. Could be either an
	 * extensive list for a whole lookup table or a subset of it matching the
	 * provided key.
	 * 
	 * 
	 * URL request: GET /lookuptables/table_name GET
	 * /lookuptables/table_name?keys=value1,valueN
	 * 
	 * @param tableName
	 *          the lookup table where the data belongs to.
	 * @param keys
	 *          the value/s for the key field/s
	 * @return 200 HTTP Status or 500 if something failed
	 */
	@GET
	@Produces("application/json")
	@Path("/{tablename}")
	public String listData(@PathParam("customer") String customer, @PathParam("tablename") String tableName,
			@QueryParam("keys") String keys) {

		String response = "";
		if (keys == null) {
			response = LookUpTableDao.getInstance().getLookupTableRecords(customer, tableName);
		} else {
			response = LookUpTableDao.getInstance().getLookupTableRecords(customer, tableName, keys);
		}

		return new LookupManagerResponse(LookupManagerResponse.HttpStatus.OK, "These are the records found", response)
				.toJson();
	}

	/**
	 * It should update the record provided it exits.
	 * 
	 * URL request: PUT
	 * /lookuptables/table_name?keys=value1,valueN&fields=value1,valueN
	 * 
	 * @param tableName
	 *          the lookup table where the data belongs to.
	 * @param keys
	 *          the value/s for the key field/s
	 * @param fields
	 * @return 200 HTTP Status or 500 if something failed
	 */
	@PUT
	@Produces("application/json")
	@Path("/{tablename}")
	public String updateData(@PathParam("customer") String customer, @PathParam("tablename") String tableName,
			@QueryParam("keys") String keys, @QueryParam("fields") String fields) {

		if (keys == null || fields == null) {
			throw new CustomWebApplicationException(Response.Status.BAD_REQUEST,
					"Can not update a record whit out providing keys and fields");
		}

		boolean sucess = LookUpTableDao.getInstance().updateLookupTableRecords(customer, tableName, keys, fields);

		LookupManagerResponse response;
		if (sucess) {
			response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.OK, "Data Update sucessful", "");
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("LookUpTable: ").append(tableName);
			builder.append("|keys: ").append(keys);
			builder.append("|fields: ").append(fields);
			builder.append("|customer: ").append(customer);

			response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.INTERNAL_SERVER_ERROR,
					"There has been an error with the data update", builder.toString());
			throw new CustomWebApplicationException(response);
		}

		return response.toJson();
	}

	/**
	 * It should update the record provided it exits.
	 * 
	 * URL request:
	 * 
	 * DELETE /lookuptables/table_name
	 * 
	 * DELETE /lookuptables/table_name?keys=value1,valueN
	 * 
	 * @param tableName
	 *          the lookup table where the data belongs to.
	 * @param keys
	 *          the value/s for the key field/s
	 * @return 200 HTTP Status or 500 if something failed
	 */
	@DELETE
	@Produces("application/json")
	@Path("/{tablename}")
	public String deleteData(@PathParam("customer") String customer, @PathParam("tablename") String tableName,
			@QueryParam("keys") String keys) {
		StringBuilder builder = new StringBuilder();

		boolean sucess;
		if (keys == null) {
			sucess = LookUpTableDao.getInstance().deleteLookupTableRecords(customer, tableName);

			builder.append("LookUpTable: ").append(tableName);
		} else {
			sucess = LookUpTableDao.getInstance().deleteLookupTableRecords(customer, tableName, keys);

			builder.append("LookUpTable: ").append(tableName);
			builder.append("|keys: ").append(keys);
			builder.append("|customer: ").append(customer);
		}

		LookupManagerResponse response;
		if (sucess) {
			response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.OK, "Data Deletion Sucessful", "");
		} else {
			response = new LookupManagerResponse(LookupManagerResponse.HttpStatus.INTERNAL_SERVER_ERROR,
					"There has been an error with the data deletion", builder.toString());
			throw new CustomWebApplicationException(response);
		}

		return response.toJson();
	}

}
