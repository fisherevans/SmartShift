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
    private Status statusObject;

    @Expose
    private String status;
	
	@Expose
    private Object data;

    /**
     * Creates a standard data structure
     * 
     * @param data the data object to return as JSON
     */
    public JsonResult(Object data) {
        this(Status.OK, data);
    }
	
	    /**
     * Creates a standard data structure, with status
     * 
     * @param status
     *            The status of the result
     * @param data
     *            The data object to return as JSON
     */
    public JsonResult(Status statusObject, Object data) {
        this.statusObject = statusObject;
        this.data = data;
        this.status = statusObject.getStatusCode() + " " + statusObject.getReasonPhrase();
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

    public static String ok(Object entity) {
        JsonResult data = new JsonResult(entity);
        return GsonFactory.toJson(data);
    }
}
