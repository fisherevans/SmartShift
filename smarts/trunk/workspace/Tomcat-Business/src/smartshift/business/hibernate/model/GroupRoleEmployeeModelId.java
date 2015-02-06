package smartshift.business.hibernate.model;

import java.io.Serializable;

public class GroupRoleEmployeeModelId implements Serializable {
    public Integer groupRoleID;
    public Integer employeeID;
    
    public GroupRoleEmployeeModelId() {
        
    }
    
    @Override
    public int hashCode() {
        return groupRoleID.hashCode() + employeeID.hashCode()*31;
    }

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof GroupRoleEmployeeModelId) {
            GroupRoleEmployeeModelId other = (GroupRoleEmployeeModelId) obj;
            return groupRoleID == other.groupRoleID && employeeID == other.employeeID;
        }
        return false;
    }
}
