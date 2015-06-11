package smarts.apidocker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Path {
	private String _name;
	
	private Map<Method, MethodBody> _bodyMap;
    private Map<String, String> _params = null;

	public Path(String name) {
		_name = name;
		_bodyMap = new HashMap<>();
        _params = new HashMap<>();
	}
    
    public Collection<String> getParams() {
        return _params.keySet();
    }
    
    public void addParamDescription(String param, String description) {
        _params.put(param, description);
    }
    
    public String getParamDescription(String param) {
        return _params.get(param);
    }
	
	public Collection<Method> getMethods() {
	    return _bodyMap.keySet();
	}
	
	public void addMethodBody(Method method, MethodBody body) {
		_bodyMap.put(method, body);
	}
	
	public MethodBody getMethodBody(Method method) {
		return _bodyMap.get(method);
	}

	public String getName() {
	    return _name;
	}
}
