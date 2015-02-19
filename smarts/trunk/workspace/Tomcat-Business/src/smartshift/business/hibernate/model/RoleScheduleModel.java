package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "RoleSchedule")
public class RoleScheduleModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "schedID", nullable = false)
    private Integer scheduleID;

    @Column(name = "grpRoleID", nullable = false)
    private Integer groupRoleID;

    /**
     * Initializes the object.
     */
    public RoleScheduleModel() {
        
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
