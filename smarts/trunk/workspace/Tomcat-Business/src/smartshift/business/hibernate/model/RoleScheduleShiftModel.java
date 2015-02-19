package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "RoleScheduleShift")
@IdClass(RoleScheduleShiftModelId.class)
public class RoleScheduleShiftModel {
    @Id
    @Column(name = "roleSchedID", nullable = false)
    private Integer roleScheduleID;

    @Id
    @Column(name = "shiftID", nullable = false)
    private Integer shiftID;

    /**
     * Initializes the object.
     */
    public RoleScheduleShiftModel() {
        
    }

    /**
     * @return the roleScheduleID
     */
    public Integer getRoleScheduleID() {
        return roleScheduleID;
    }

    /**
     * @param roleScheduleID the roleScheduleID to set
     */
    public void setRoleScheduleID(Integer roleScheduleID) {
        this.roleScheduleID = roleScheduleID;
    }

    /**
     * @return the shiftID
     */
    public Integer getShiftID() {
        return shiftID;
    }

    /**
     * @param shiftID the shiftID to set
     */
    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }
}
