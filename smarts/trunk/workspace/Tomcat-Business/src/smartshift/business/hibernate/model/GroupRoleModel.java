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
import smartshift.common.security.Authentication;
import smartshift.common.util.collections.ROList;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "GroupRole")
public class GroupRoleModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "grpID")
    private GroupModel group;
    
    @ManyToOne
    @JoinColumn(name = "roleID")
    private RoleModel role;
    
    @ManyToMany(mappedBy="groupRoles", cascade=CascadeType.ALL)
    private List<EmployeeModel> employees;

    public GroupRoleModel() {
    }

    public GroupRoleModel(GroupModel group, RoleModel role) {
        super();
        this.group = group;
        this.role = role;
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
     * @return the group
     */
    public GroupModel getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(GroupModel group) {
        this.group = group;
    }

    /**
     * @return the role
     */
    public RoleModel getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(RoleModel role) {
        this.role = role;
    }
    
    
}
