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
@Table(name = "EmpScheduleShift")
@IdClass(EmployeeScheduleShiftModelId.class)
public class EmployeeScheduleShiftModel {
    @Id
    @Column(name = "empSchedID", nullable = false)
    private Integer employeeScheduleID;

    @Id
    @Column(name = "shiftID", nullable = false)
    private Integer shiftID;

    @Column(name = "grpRoleID", nullable = false)
    private Integer groupRoleID;

    /**
     * Initializes the object.
     */
    public EmployeeScheduleShiftModel() {
        
    }

    /**
     * @return the employeeScheduleID
     */
    public Integer getEmployeeScheduleID() {
        return employeeScheduleID;
    }

    /**
     * @param employeeScheduleID the employeeScheduleID to set
     */
    public void setEmployeeScheduleID(Integer employeeScheduleID) {
        this.employeeScheduleID = employeeScheduleID;
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

    /**
     * @return the groupRoleID
     */
    public Integer getGroupRoleID() {
        return groupRoleID;
    }

    /**
     * @param groupRoleID the groupRoleID to set
     */
    public void setGroupRoleID(Integer groupRoleID) {
        this.groupRoleID = groupRoleID;
    }
}
