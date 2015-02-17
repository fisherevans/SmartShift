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
@Table(name = "`Group`")
@NamedQueries({
        @NamedQuery(name = GroupModel.GET_EMPLOYEE_GROUPS,
                    query = "select g from GroupModel g "
                          + "where g.id in (select ge.groupID "
                          +                "from GroupEmployeeModel ge "
                          +                "where ge.employeeID = :" + GroupModel.GET_EMPLOYEE_GROUPS_EMP_ID
                          + ")"
        )
})
public class GroupModel {
    /**
     * named query identifier to get the groups an employe belongs to
     */
    public static final String GET_EMPLOYEE_GROUPS = "getEmployeeGroups";
    
    /**
     * named query parameter for GET_EMPLOYEE_GROUPS - the employee id
     */
    public static final String GET_EMPLOYEE_GROUPS_EMP_ID = "empoyeeIDParam";
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "parentID")
    private Integer parentID;

    @Column(name = "name", length = 45)
    private String name;

    /**
     * Initializes the object.
     */
    public GroupModel() {
    }

    /**
     * Initializes the object.
     * @param parentID the parent id, can be null
     * @param name the name of the group
     */
    public GroupModel(Integer parentID, String name) {
        super();
        this.parentID = parentID;
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
     * @return the parentID
     */
    public Integer getParentID() {
        return parentID;
    }

    /**
     * @param parentID the parentID to set
     */
    public void setParentID(Integer parentID) {
        this.parentID = parentID;
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
