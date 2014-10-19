package smarts.apidocker;

public enum Method {
    PUT,
	GET,
	POST,
	DELETE;
	
	public static Method getMethod(String str) {
	    if("GET".equalsIgnoreCase(str))
            return Method.GET;
        else if("PUT".equalsIgnoreCase(str))
            return Method.PUT;
        else if("POST".equalsIgnoreCase(str))
            return Method.POST;
        else if("DELETE".equalsIgnoreCase(str))
            return Method.DELETE;
        else 
            return null;
	}
}
