package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Entity
@Table(name = "GroupRoleEmployee")
@IdClass(GroupRoleEmployeeModelId.class)
public class GroupRoleEmployeeModel {
    @Id
    @Column(name = "grpRoleID", nullable = false)
    private Integer groupRoleID;

    @Id
    @Column(name = "empID", nullable = false)
    private Integer employeeID;

    public GroupRoleEmployeeModel() {
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
}
