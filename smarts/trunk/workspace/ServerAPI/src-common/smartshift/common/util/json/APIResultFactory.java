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
    /**
     * Generate a web application exception and an internal server error header
     * 
     * @return The throwable WebApplicationException
     */
    public static WebApplicationException getInternalErrorException() {
        return getException(JsonResultStatus.Error, Status.INTERNAL_SERVER_ERROR, "An internal error has occured!");
    }

    /**
     * Generate a web application exception with an unauthorized server error
     * header
     * 
     * @return The throwable WebApplicationException
     */
    public static WebApplicationException getUnauthorizedErrorException() {
        return getException(JsonResultStatus.Unauthorized, Status.UNAUTHORIZED,
                "You are not authorized to access this method.");
    }

	/**
	 * Generate a web application exception and a bad request header
	 * @param errorMessage The error message to display
	 * @return The throwable WebApplicationException
	 */
	public static WebApplicationException getBadRequestException(String errorMessage) {
        return getException(JsonResultStatus.Error, Status.BAD_REQUEST, errorMessage);
	}

    /**
     * Generate the standard web application exception
     * 
     * @param apiStatus The API status result
     * @param status The HTTP status to return
     * @param errorMessage The error message to display
     * @return The throwable WebApplicationException
     */
    public static WebApplicationException getException(JsonResultStatus apiStatus, Status status, String errorMessage) {
        String json = new JsonResult(apiStatus, errorMessage).toJson();
        WebApplicationException e = new WebApplicationException(Response.status(status).entity(json).build());
        return e;
    }
}
