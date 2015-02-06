package smartshift.business.hibernate.model;

import java.io.Serializable;

public class GroupEmployeeModelId implements Serializable {
    public Integer groupID;
    public Integer employeeID;
    
    public GroupEmployeeModelId() {
        
    }
    
    @Override
    public int hashCode() {
        return groupID.hashCode() + employeeID.hashCode()*31;
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof GroupEmployeeModelId) {
            GroupEmployeeModelId other = (GroupEmployeeModelId) obj;
            return groupID == other.groupID && employeeID == other.employeeID;
        }
        return false;
    }
}
