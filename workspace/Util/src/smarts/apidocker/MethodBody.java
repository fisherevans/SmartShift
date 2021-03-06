package smarts.apidocker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodBody {
	private String _description = null;
    private Map<Integer, Response> _responses = null;
	private String _takes = "";
	private List<String> _requires;
	
	public MethodBody(String description) {
	    _description = description;
        _responses = new HashMap<>();
        _requires = new ArrayList<String>();
    }
    
    public Collection<Integer> getCodes() {
        return _responses.keySet();
    }
    
    public void addResponse(Integer code, Response body) {
        _responses.put(code, body);
    }
    
    public Response getResponse(Integer code) {
        return _responses.get(code);
    }
	
	public void setDescription(String description) {
        _description = description;
	}
	
	public String getDescription() {
	    return _description;
	}
	
	public void appendTakes(String line) {
	    _takes += line + "\n";
	}
	
	public void setTakes(String takes) {
	    _takes = takes;
	}
	
	public String getTakes() {
	    return _takes;
	}
	
	public List<String> getRequires() {
	    return _requires;
	}
}
