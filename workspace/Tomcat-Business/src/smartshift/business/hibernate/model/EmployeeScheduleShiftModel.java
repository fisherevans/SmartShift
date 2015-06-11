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

    @Column(name = "grpID", nullable = false)
    private Integer groupID;
    
    @Column(name = "roleID", nullable = true)
    private Integer roleID;

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
     * @return the groupID
     */
    public Integer getGroupID() {
        return groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    
    /**
     * @return the roleID
     */
    public Integer getRoleID() {
        return roleID;
    }
    
    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }
}
