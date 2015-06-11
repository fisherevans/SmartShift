package smartshift.business.hibernate.model;

import java.io.Serializable;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class GroupRoleEmployeeModelId implements Serializable {
    private static final long serialVersionUID = 6808506517404398625L;

    /**
     * the group id
     */
    public Integer groupRoleID;
    
    /**
     *  the employee id
     */
    public Integer employeeID;
    
    /**
     * Initializes the object.
     */
    public GroupRoleEmployeeModelId() {
        
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return groupRoleID.hashCode() + employeeID.hashCode()*31;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof GroupRoleEmployeeModelId) {
            GroupRoleEmployeeModelId other = (GroupRoleEmployeeModelId) obj;
            return groupRoleID == other.groupRoleID && employeeID == other.employeeID;
        }
        return false;
    }
}
