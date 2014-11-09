package smartshift.common.util.json;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A set of utility functions for standardized api results.
 */
public class APIResultFactory {

    public static Response getInvalidCredentialsResponse() {
        return getResponse(Status.UNAUTHORIZED, null, "Invalid credentials!");
    }

    /**
     * Gets a Response containing the passed status
     * 
     * Passes a null result
     * 
     * @see smartshift.common.util.json.APIResultFactory#getResponse(Status,
     * Object)
     */
    public static Response getResponse(Status status) {
        return getResponse(status, null);
    }
    
    public static Response getResponse(Status status, Object result) {
        return getResponse(status, result, null);
    }


    public static Response getResponse(Status status, Object result, String message) {
        return getResponseBuilder(status, result, message).build();
    }

    /**
     * Generate the standard web application exception
     * 
     * @param status The HTTP status to return
     * @param result The result of the call
     * @param message The message to include
     * @return The Response Builder
     */
    public static ResponseBuilder getResponseBuilder(Status status, Object result, String message) {
        String json = GsonFactory.toJson(new JsonResult(status, result, message));
        return Response.ok(json);
    }
}
