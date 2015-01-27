package smartshift.business.hibernate.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.mindrot.jbcrypt.BCrypt;
import com.google.gson.annotations.Expose;
import smartshift.common.util.collections.ROList;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Employee")
public class EmployeeModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fName", length = 50)
    private String firstName;

    @Column(name = "lName", length = 50)
    private String lastName;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "defaultGrpID")
    private GroupModel defaultGroup;
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="GroupRoleEmployee",
        joinColumns={@JoinColumn(name="empID", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="grpRoleID", referencedColumnName="id")})
    private List<GroupRoleModel> groupRoles;

    public EmployeeModel() {
    }

    public EmployeeModel(String firstName, String lastName, GroupModel defaultGroup) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.defaultGroup = defaultGroup;
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
     * @return the defaultGroup
     */
    public GroupModel getDefaultGroup() {
        return defaultGroup;
    }

    /**
     * @param defaultGroup the defaultGroup to set
     */
    public void setDefaultGroup(GroupModel defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    /**
     * @return the groupRoles
     */
    public List<GroupRoleModel> getGroupRoles() {
        return groupRoles;
    }
}
