package smartshift.business.hibernate.model;

import java.io.Serializable;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * used to map the getActiveSessions query on SessionModel
 */
public class GroupEmployeeModelId implements Serializable {
    private static final long serialVersionUID = 4797225070470231857L;
    /**
     * the group id
     */
    public Integer groupID;
    
    /**
     * the employee id
     */
    public Integer employeeID;
    
    /**
     * initializer
     */
    public GroupEmployeeModelId() {
        
    }

    /**
     * Initializes the object.
     * @param groupID
     * @param employeeID
     */
    public GroupEmployeeModelId(Integer groupID, Integer employeeID) {
        this.groupID = groupID;
        this.employeeID = employeeID;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return groupID.hashCode() + employeeID.hashCode()*31;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof GroupEmployeeModelId) {
            GroupEmployeeModelId other = (GroupEmployeeModelId) obj;
            return groupID == other.groupID && employeeID == other.employeeID;
        }
        return false;
    }
}
