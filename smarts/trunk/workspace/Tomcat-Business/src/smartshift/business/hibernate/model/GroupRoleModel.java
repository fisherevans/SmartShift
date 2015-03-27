package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "GroupRole")
@NamedQueries({
    @NamedQuery(name = GroupRoleModel.GET_GROUP_ROLES_BY_EMPLOYEE,
            query = "select gr "
                  + "  from GroupRoleModel gr "
                  + " where gr.id in ( "
                  + "         select gre.groupRoleID "
                  + "           from GroupRoleEmployeeModel gre "
                  + "          where gre.employeeID = :" + GroupRoleModel.GET_GROUP_ROLES_BY_EMPLOYEE_EMP_ID
                  + "       )"
    )
})
public class GroupRoleModel {
    /**
     * named query - lookup group roles an emp belongs to
     */
    public static final String GET_GROUP_ROLES_BY_EMPLOYEE = "getGroupRoleByEmployee";

    /**
     * the employee id param for GET_GROUP_ROLES_BY_EMPLOYEE
     */
    public static final String GET_GROUP_ROLES_BY_EMPLOYEE_EMP_ID = "employeeID";
    
    /**
     * named query - lookup the group roles and their capability
     */
    public static final String GET_GROUP_ROLE_CAPS = "getGroupRoleCapability";

    /**
     * the group id param for GET_GROUP_ROLE_CAPS
     */
    public static final String GET_GROUP_ROLE_CAPS_GROUP_ID = "groupID";
    
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "grpID", nullable = false)
    private Integer groupID;
    
    @Column(name = "roleID", nullable = false)
    private Integer roleID;

    /**
     * Initializes the object.
     */
    public GroupRoleModel() {
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
