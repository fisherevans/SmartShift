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
@Table(name = "Employee")
@NamedQueries({
        @NamedQuery(name = EmployeeModel.GET_GROUP_EMPLOYEES,
                    query = "select e from EmployeeModel e "
                          + "where e.id in (select ge.employeeID "
                          +                "from GroupEmployeeModel ge "
                          +                "where ge.groupID = :" + EmployeeModel.GET_GROUP_EMPLOYEES_GROUP_ID
                          + ")"
        ),
        @NamedQuery(name = EmployeeModel.GET_GROUP_ROLE_EMPLOYEES,
        query = "select e "
              + "  from EmployeeModel e "
              + " where e.id in ( "
              + "         select gre.employeeID "
              + "           from GroupRoleEmployeeModel gre "
              + "          where gre.groupRoleID in ( "
              + "                  select gr.id "
              + "                    from GroupRoleModel gr "
              + "                   where gr.groupID = :" + EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_GROUP_ID
              + "                     and gr.roleID = :" + EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_ROLE_ID
              + "                ) "
              + "       )"
        ),
        @NamedQuery(name = EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_BY_GR,
        query = "select e "
              + "  from EmployeeModel e "
              + " where e.id in ( "
              + "         select gre.employeeID "
              + "           from GroupRoleEmployeeModel gre "
              + "          where gre.groupRoleID = :" + EmployeeModel.GET_GROUP_ROLE_EMPLOYEES_BY_GR_GR_ID
              + "       )"
        )
})
public class EmployeeModel {
    /**
     * named query to get employees in group
     */
    public static final String GET_GROUP_EMPLOYEES = "getGroupEmployees";
    
    /**
     * groupi id for GET_GROUP_EMPLOYEES
     */
    public static final String GET_GROUP_EMPLOYEES_GROUP_ID = "groupID";
    
    /**
     * named query to get employees in a group role
     */
    public static final String GET_GROUP_ROLE_EMPLOYEES = "getGroupRoleEmployees";
    
    /**
     * group id for GET_GROUP_ROLE_EMPLOYEES
     */
    public static final String GET_GROUP_ROLE_EMPLOYEES_GROUP_ID = "groupID";
    
    /**
     * role id for GET_GROUP_ROLE_EMPLOYEES
     */
    public static final String GET_GROUP_ROLE_EMPLOYEES_ROLE_ID = "roleID";
    
    /**
     * named query to get employee in group role by gr id
     */
    public static final String GET_GROUP_ROLE_EMPLOYEES_BY_GR = "getGroupRoleEmployeesByGR";
    
    /**
     * group role id for GET_GROUP_ROLE_EMPLOYEES_BY_GR
     */
    public static final String GET_GROUP_ROLE_EMPLOYEES_BY_GR_GR_ID = "groupRoleID";
    
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "defaultGrpID", nullable = false)
    private Integer defaultGroupID;

    @Column(name = "fName", length = 60)
    private String firstName;

    @Column(name = "lName", length = 60)
    private String lastName;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    /**
     * Initializes the object.
     */
    public EmployeeModel() {
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the defaultGroupID
     */
    public Integer getDefaultGroupID() {
        return defaultGroupID;
    }

    /**
     * @param defaultGroupID the defaultGroupID to set
     */
    public void setDefaultGroupID(Integer defaultGroupID) {
        this.defaultGroupID = defaultGroupID;
    }

    /**
     * @return the active flag
     */
	public Boolean getActive() {
		return active;
	}

    /**
     * @param active the active flag
     */
	public void setActive(Boolean active) {
		this.active = active;
	}
    
    /** Overridden method - see parent javadoc
      * @see java.lang.Object#toString()
      */
    @Override
    public String toString() {
        String out = "";
        out += "id:" + id + ", ";
        out += "defaultGroupID:" + defaultGroupID + ", ";
        out += "firstName:" + firstName + ", ";
        out += "lastName:" + lastName + ", ";
        out += "active:" + active;
        return out;
    }
}
