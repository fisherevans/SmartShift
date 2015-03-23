package smartshift.business.updates;

import smartshift.business.cache.bo.Employee;

public abstract class BaseUpdate {
    private final String _type;
    
    private final Object _id;
    
    private final Employee _executer;
    
    public BaseUpdate(String type, Object id, Employee executer) {
        _type = type;
        _id = id;
        _executer = executer;
    }
    
    public String getType() {
        return _type;
    }
    
    public Object getID() {
        return _id;
    }
    
    public Employee getExecuter() {
        return _executer;
    }
    
    public abstract Object getJSONObject();

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
