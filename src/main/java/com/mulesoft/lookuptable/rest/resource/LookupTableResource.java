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

import com.mulesoft.lookuptable.rest.exceptions.CustomWebApplicationException;
/**
 * This class represent the LookUp Table Resource.
 * This resource expose an interface for CRUD operations over any lookup table provided.
 * 
 * It acts as a facade for the actual persistence layer.
 * @author damiansima
 *
 */
@Path("/lookuptables")
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
	 * @return
	 */
	@POST
	@Produces("text/plain")
	@Path("/{tablename}")
	public String createData(@PathParam("tablename") String tableName, @QueryParam("keys") String keys,
			@QueryParam("fields") String fields) {
		StringBuilder builder = new StringBuilder();

		if (keys == null || fields == null) {
			throw new CustomWebApplicationException(Response.Status.BAD_REQUEST,
					"Can not create a record whit out providing keys and fields");
		}

		builder.append("200 - ");
		builder.append("CREATING... LookUpTable: ").append(tableName);
		builder.append("|keys: ").append(keys);
		builder.append("|fields: ").append(fields);
		return builder.toString();

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
	 * @return
	 */
	@GET
	@Produces("text/plain")
	@Path("/{tablename}")
	public String listData(@PathParam("tablename") String tableName, @QueryParam("keys") String keys) {
		StringBuilder builder = new StringBuilder();

		if (keys == null) {
			builder.append("200 - ");
			builder.append("LISTING... LookUpTable: ").append(tableName);
		} else {
			builder.append("200 - ");
			builder.append("LISTING... LookUpTable: ").append(tableName);
			builder.append("|keys: ").append(keys);
		}

		return builder.toString();
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
	 * @return
	 */
	@PUT
	@Produces("text/plain")
	@Path("/{tablename}")
	public String updateData(@PathParam("tablename") String tableName, @QueryParam("keys") String keys,
			@QueryParam("fields") String fields) {
		StringBuilder builder = new StringBuilder();

		if (keys == null || fields == null) {
			throw new CustomWebApplicationException(Response.Status.BAD_REQUEST,
					"Can not update a record whit out providing keys and fields");
		}
		builder.append("200 - ");
		builder.append("UPDATING... LookUpTable: ").append(tableName);
		builder.append("|keys: ").append(keys);

		return builder.toString();
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
	 * @return
	 */
	@DELETE
	@Produces("text/plain")
	@Path("/{tablename}")
	public String deleteData(@PathParam("tablename") String tableName, @QueryParam("keys") String keys) {
		StringBuilder builder = new StringBuilder();

		if (keys == null) {
			builder.append("200 - ");
			builder.append("DELETING... LookUpTable: ").append(tableName);
		} else {
			builder.append("200 - ");
			builder.append("DELETING... LookUpTable: ").append(tableName);
			builder.append("|keys: ").append(keys);
		}

		return builder.toString();

	}

}
