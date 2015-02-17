package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 */
@Entity
@Table(name = "GroupEmployee")
@IdClass(GroupEmployeeModelId.class)
public class GroupEmployeeModel {
    @Id
    @Column(name = "grpID", nullable = false)
    private Integer groupID;

    @Id
    @Column(name = "empID", nullable = false)
    private Integer employeeID;

    /**
     * Initializes the object.
     */
    public GroupEmployeeModel() {
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
