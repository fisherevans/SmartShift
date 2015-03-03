package smartshift.common.jersey;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import smartshift.common.util.json.APIResultFactory;

/**
 * Base object for Jersey Actions. Gain access to context variables (including the user)
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public abstract class BaseActions {
    /**
     * The HTTP Request
     */
    @Context
    HttpServletRequest _request;
    
    /**
     * HTTP Context
     */
    @Context
    private ServletContext _context;
    
    /**
     * @return the HTTP Context
     */
    protected ServletContext getContext() {
        return _context;
    }

    /**
     * @return The HTTP Request
     */
    protected HttpServletRequest getRequest() {
        return _request;
    }

    /**
     * Gets a canned data response
     * @param status the http status of the response
     * @param object The data to return
     * @return The built response object
     */
    public static Response getObjectResponse(Status status, Object object) {
        return APIResultFactory.getResponse(status, object, null);
    }

    /**
     * Gets a canned message response
     * @param status the http status of the response
     * @param message The message to return
     * @return The built response object
     */
    public static Response getMessageResponse(Status status, String message) {
        return APIResultFactory.getResponse(status, null, message);
    }
}
