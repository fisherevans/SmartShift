package smartshift.business.updates;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import smartshift.business.cache.bo.Employee;
import smartshift.business.jersey.objects.EmployeeJSON;

public abstract class BaseUpdate {
    private final String _type, _subType;
    
    private final Object _id;
    
    private final Employee _executer;
    
    private final Date _timestamp;
    
    public BaseUpdate(String type, String subType, Object id, Employee executer) {
        _type = type;
        _subType = subType;
        _id = id;
        _executer = executer;
        _timestamp = new Date();
    }
    
    public BaseUpdate(String type, Object id, Employee executer) {
        this(type, null, id, executer);
    }
    
    public String getType() {
        return _type;
    }
    
    public String getSubType() {
        return _subType;
    }
    
    public Object getID() {
        return _id;
    }
    
    public Employee getExecuter() {
        return _executer;
    }
    
    public Date getTimestamp() {
        return _timestamp;
    }
    
    public Map<String, Object> getJSONMap() {
        Map<String, Object> json = new HashMap<>();
        if(_subType != null) json.put("subType", _subType);
        if(_executer != null) json.put("executer", new EmployeeJSON(_executer));
        if(_timestamp != null) json.put("timestamp", _timestamp);
        return json;
    }

    @Override
    public int hashCode() {
        return _type.hashCode() + _id.hashCode()*111;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BaseUpdate) {
            BaseUpdate other = (BaseUpdate) obj;
            return _type.equals(other._type) && _id.equals(other._id); 
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("[Type:%s ID:%s E:%s]", _type, _id, _executer);
    }
}
