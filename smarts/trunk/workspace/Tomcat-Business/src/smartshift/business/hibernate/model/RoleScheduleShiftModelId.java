package smartshift.business.hibernate.model;

import java.io.Serializable;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
public class RoleScheduleShiftModelId implements Serializable {
    private static final long serialVersionUID = 6808506517404398625L;

    /**
     * the role schedule id
     */
    public Integer roleScheduleID;
    
    /**
     *  the shift id
     */
    public Integer shiftID;
    
    /**
     * Initializes the object.
     */
    public RoleScheduleShiftModelId() {
        
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return roleScheduleID.hashCode() + shiftID.hashCode()*31;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof RoleScheduleShiftModelId) {
            RoleScheduleShiftModelId other = (RoleScheduleShiftModelId) obj;
            return roleScheduleID == other.roleScheduleID && shiftID == other.shiftID;
        }
        return false;
    }
}
