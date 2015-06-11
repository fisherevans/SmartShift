package smartshift.business.hibernate.model;

import java.io.Serializable;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 */
public class GroupRoleCapabilityID implements Serializable {
    private static final long serialVersionUID = 4797225070470231857L;
    /**
     * the groupRoleID id
     */
    public Integer groupRoleID;
    
    /**
     * the capabilityID id
     */
    public Integer capabilityID;
    
    /**
     * initializer
     */
    public GroupRoleCapabilityID() {
        
    }

    /**
     * Initializes the object.
     * @param groupRoleID
     * @param capabilityID
     */
    public GroupRoleCapabilityID(Integer groupRoleID, Integer capabilityID) {
        this.groupRoleID = groupRoleID;
        this.capabilityID = capabilityID;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return groupRoleID.hashCode() + groupRoleID.hashCode()*31;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof GroupRoleCapabilityID) {
            GroupRoleCapabilityID other = (GroupRoleCapabilityID) obj;
            return groupRoleID == other.groupRoleID && capabilityID == other.capabilityID;
        }
        return false;
    }
}