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
    public static final String GET_GROUP_EMPLOYEES = "getGroupEmployees";
    
    public static final String GET_GROUP_EMPLOYEES_GROUP_ID = "groupID";
    
    public static final String GET_GROUP_ROLE_EMPLOYEES = "getGroupRoleEmployees";
    
    public static final String GET_GROUP_ROLE_EMPLOYEES_GROUP_ID = "groupID";
    
    public static final String GET_GROUP_ROLE_EMPLOYEES_ROLE_ID = "roleID";
    
    public static final String GET_GROUP_ROLE_EMPLOYEES_BY_GR = "getGroupRoleEmployeesByGR";
    
    public static final String GET_GROUP_ROLE_EMPLOYEES_BY_GR_GR_ID = "groupRoleID";
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "defaultGrpID", nullable = false)
    private Integer defaultGroupID;

    @Column(name = "fName", length = 60)
    private String firstName;

    @Column(name = "lName", length = 60)
    private String lastName;
    
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
}
