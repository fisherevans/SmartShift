package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Role")
@NamedQueries({
    @NamedQuery(name = RoleModel.GET_GROUP_ROLES,
            query = "select r from RoleModel r "
                  + "where r.id in (select gr.roleID "
                  +                "from GroupRoleModel gr "
                  +                "where gr.groupID = :" + RoleModel.GET_GROUP_ROLES_GRP_ID
                  + ")"
    ),
    @NamedQuery(name = RoleModel.GET_GROUP_CAP_ROLES,
            query = "select r from RoleModel r "
                  + "where r.id in (select gr.roleID "
                  +                "from GroupRoleModel gr "
                  +                "where gr.groupID = :" + RoleModel.GET_GROUP_CAP_ROLES_GRP_ID + " "
                  +                "and gr.capabilityID = :" + RoleModel.GET_GROUP_CAP_ROLES_CAP_ID
                  + ")"
    ),
    @NamedQuery(name = RoleModel.GET_EMPLOYEE_GROUP_ROLES,
            query = "select r "
                  + "  from RoleModel r "
                  + " where r.id in ( "
                  + "  select gr.roleID "
                  + "    from GroupRoleModel gr "
                  + "   where gr.id in ( "
                  + "    select gre.groupRoleID "
                  + "      from GroupRoleEmployeeModel gre "
                  + "     where gre.employeeID = :" + RoleModel.GET_EMPLOYEE_GROUP_ROLES_EMP_ID
                  + "   ) and gr.groupID = :" + RoleModel.GET_EMPLOYEE_GROUP_ROLES_GRP_ID
                  + " ) "
    )
})
public class RoleModel {
    /**
     * named query - gets all roles in a group
     */
    public static final String GET_GROUP_ROLES = "getGroupRoles";

    /**
     * group id param for GET_GROUP_ROLES
     */
    public static final String GET_GROUP_ROLES_GRP_ID = "groupIDParam";
    /**
     * named query - gets all roles in a group
     */
    public static final String GET_GROUP_CAP_ROLES = "getGroupCapabilityRoles";

    /**
     * group id param for GET_GROUP_CAP_ROLES
     */
    public static final String GET_GROUP_CAP_ROLES_GRP_ID = "groupIDParam";

    /**
     * capability id param for GET_GROUP_CAP_ROLES
     */
    public static final String GET_GROUP_CAP_ROLES_CAP_ID = "capIDParam";
    
    /**
     * get all roles given and employee and group
     */
    public static final String GET_EMPLOYEE_GROUP_ROLES = "getEmployeeGroupRoles";

    /**
     * group id for GET_EMPLOYEE_GROUP_ROLES
     */
    public static final String GET_EMPLOYEE_GROUP_ROLES_GRP_ID = "groupIDParam";

    /**
     * emp id for GET_EMPLOYEE_GROUP_ROLES
     */
    public static final String GET_EMPLOYEE_GROUP_ROLES_EMP_ID = "employeeIDParam";
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    /**
     * Initializes the object.
     */
    public RoleModel() {
    }

    /**
     * Initializes the object.
     * @param name
     */
    public RoleModel(String name) {
        this.name = name;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
