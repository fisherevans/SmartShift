package smartshift.common.util.json;

import javax.ws.rs.core.Response.Status;
import com.google.gson.annotations.Expose;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          The standard return data structure of JSON requests
 */
public class JsonResult {
    /** The HTTP status object */
    private Status statusObject;
	
    /** The data object to return to the client */
	@Expose
    private Object data;
	
	/** The text message describing the result */
	@Expose
	private String message;

    /**
     * Creates a standard data structure, with a 200 response and a data object
     * 
     * @param data the data object to return as JSON
     */
    public JsonResult(Object data) {
        this(Status.OK, data);
    }
    
    /**
     * Creates a standard data structure, with status
     * 
     * @param statusObject
     *            The http status of the result
     * @param data
     *            The data object to return as JSON
     */
    public JsonResult(Status statusObject, Object data) {
        this(statusObject, data, null);
    }
    
    /**
     * Creates a standard data structure, with status
     * 
     * @param statusObject
     *            The http status of the result
     * @param data
     *            The data object to return as JSON
     * @param message
     *            The text message to return
     */
    public JsonResult(Status statusObject, Object data, String message) {
        this.statusObject = statusObject;
        this.data = data;
        this.message = message;
        // this.status = statusObject.getStatusCode() + " " + statusObject.getReasonPhrase();
    }
	
    /**
     * @return the result's status
     */
    public Status getStatusObject() {
        return statusObject;
	}
	
    /**
     * @return the result's data
     */
    public Object getData() {
        return data;
	}
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * A helper enerates the JSON text of the data passes
     * @param entity the entity to encode
     * @return the json text
     */
    public static String ok(Object entity) {
        JsonResult data = new JsonResult(entity);
        return GsonFactory.toJson(data);
    }
}
