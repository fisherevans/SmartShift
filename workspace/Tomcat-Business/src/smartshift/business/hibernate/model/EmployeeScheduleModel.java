package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "EmpSchedule")
public class EmployeeScheduleModel {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "schedID", nullable = false)
    private Integer scheduleID;

    @Column(name = "empID", nullable = false)
    private Integer employeeID;

    @Column(name = "locked", nullable = false)
    private Boolean locked;

    /**
     * Initializes the object.
     */
    public EmployeeScheduleModel() {
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the scheduleID
     */
    public Integer getScheduleID() {
        return scheduleID;
    }

    /**
     * @param scheduleID the scheduleID to set
     */
    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    /**
     * @return the employeeID
     */
    public Integer getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * @return the locked
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
