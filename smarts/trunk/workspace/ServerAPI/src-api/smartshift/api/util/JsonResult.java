package smartshift.api.util;

import com.google.gson.annotations.Expose;

import smartshift.api.util.JsonEntity;

/**
 * The standard return data structure of JSON requests
 * 
 * @author fevans
 *
 */
public class JsonResult extends JsonEntity {
	@Expose
	private String status = Status.Success.toString();
	
	@Expose
	private Object data = null;
	
	/**
	 * Creates a standard data structure
	 * @param status The status of the result
	 * @param data The data object to return as JSON
	 */
	public JsonResult(Status status, Object data) {
		this.status = status.toString();
		this.data = data;
	}
	
	public JsonResult(Object data) {
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Object getData() {
		return data;
	}
	
	/**
	 * The set of predefined result statuses
	 * @author fevans
	 */
	public enum Status {
		Error("error"),
		Success("success");
		
		private String stringValue;
		
		Status(String stringValue) {
			this.stringValue = stringValue;
		}
		
		public String toString() {
			return stringValue;
		}
	}
}
