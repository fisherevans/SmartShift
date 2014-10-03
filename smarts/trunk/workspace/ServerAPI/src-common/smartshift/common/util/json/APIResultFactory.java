package smartshift.common.util.json;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A set of utility functions for standardized ai results.
 */
public class APIResultFactory {
	private static final JsonResult.Status ERROR = JsonResult.Status.Error;
	
	private static final JsonResult.Status SUCCESS = JsonResult.Status.Success;

	/**
	 * Generate a web application exception and an internal server error header
	 * @return The throwable WebApplicationException
	 */
	public static WebApplicationException getInternalErrorException() {
		return getErrorException(Status.INTERNAL_SERVER_ERROR, "An internal error has occured!");
	}

	/**
	 * Generate a web application exception and a bad request header
	 * @param errorMessage The error message to display
	 * @return The throwable WebApplicationException
	 */
	public static WebApplicationException getBadRequestException(String errorMessage) {
		return getErrorException(Status.BAD_REQUEST, errorMessage);
	}
	
	/**
	 * Generate the standard error web application exception
	 * @param status The HTTP status to return
	 * @param errorMessage The error message to display
	 * @return The throwable WebApplicationException
	 */
	public static WebApplicationException getErrorException(Status status, String errorMessage) {
		String json = new JsonResult(ERROR, errorMessage).toJson();
		WebApplicationException e = new WebApplicationException(Response
				.status(status)
				.entity(json)
				.build()
			);
		return e;
	}
}
