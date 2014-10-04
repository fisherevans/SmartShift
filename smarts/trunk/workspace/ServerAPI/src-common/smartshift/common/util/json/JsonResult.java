package smartshift.common.util.json;

import com.google.gson.annotations.Expose;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          The standard return data structure of JSON requests
 */
public class JsonResult extends JsonEntity {
	@Expose
    private String status = JsonResultStatus.Success.toString();
	
	@Expose
	private Object data = null;
	
	    /**
     * Creates a standard data structure, with status
     * 
     * @param status
     *            The status of the result
     * @param data
     *            The data object to return as JSON
     */
    public JsonResult(JsonResultStatus status, Object data) {
		this.status = status.toString();
		this.data = data;
	}
	
    /**
     * Creates a standard data structure
     * 
     * @param data
     *            the data object to return as JSON
     */
	public JsonResult(Object data) {
		this.data = data;
	}
	
    /**
     * @return the result's status
     */
	public String getStatus() {
		return status;
	}
	
    /**
     * @return the result's data
     */
	public Object getData() {
		return data;
	}
}
