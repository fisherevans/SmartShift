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
     * Gets a WebApplicationException containing the passed status and the
     * standard return object format
     * 
     * Passes a null result
     * 
     * @see smartshift.common.util.json.APIResultFactory#getException(Status,
     * String)
     */
    public static WebApplicationException getException(Status status) {
        return getException(status, null);
    }

    /**
     * Generate the standard web application exception
     * 
     * @param status The HTTP status to return
     * @param result The result of the call
     * @return The throwable WebApplicationException
     */
    public static WebApplicationException getException(Status status, Object result) {
        String json = GsonFactory.toJson(new JsonResult(status, result));
        WebApplicationException e = new WebApplicationException(Response.status(status).entity(json).build());
        return e;
    }
}
