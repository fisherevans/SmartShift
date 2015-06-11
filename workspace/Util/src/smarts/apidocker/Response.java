package smarts.apidocker;

public class Response {
	private String _description;
	private String _exampleResponse = "";
	
	public Response(String description) {
		_description = description;
	}

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }
    
    public void appendExampleResponse(String line) {
        _exampleResponse += line + "\n";
    }

    public String getExampleResponse() {
        return _exampleResponse;
    }

    public void setExampleResponse(String exampleResponse) {
        _exampleResponse = exampleResponse;
    }
}
