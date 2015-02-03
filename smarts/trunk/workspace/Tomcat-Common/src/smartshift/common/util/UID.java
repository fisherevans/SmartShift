package smartshift.common.util;

public class UID {
    private String _type;
    private Integer _id;
    
    public UID(Identifiable obj) {
        _id = obj.getID();
        _type = obj.typeCode();
    }
    
    public UID(String type, int id) {
        _id = id;
        _type = type;
    }
    
    public int id() {
        return _id;
    }
    
    public String getType() {
        return _type;
    }
    
    @Override
    public int hashCode() {
        return _type.hashCode() * 13 + _id.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof UID) {
            return ((UID)o).getType().equals(getType()) && ((UID)o).id() == _id;
        }
        return false;
    }
}
