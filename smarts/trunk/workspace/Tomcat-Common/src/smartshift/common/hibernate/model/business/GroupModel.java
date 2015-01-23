package smartshift.common.hibernate.model.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Cacheable;
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
import smartshift.common.hibernate.model.accounts.ImageModel;
import smartshift.common.security.Authentication;
import smartshift.common.util.collections.ROList;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Group")
public class GroupModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "parentID")
    private GroupModel parent;

    @OneToMany(mappedBy = "parent")
    private List<GroupModel> children;

    @OneToMany(mappedBy = "group")
    private List<GroupRoleModel> groupRoles;

    @OneToMany(mappedBy = "group")
    private List<EmployeeModel> employees;

    @Column(name = "name", length = 50)
    private String name;

    public GroupModel() {
    }

    public GroupModel(GroupModel parent, String name) {
        super();
        this.parent = parent;
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
     * @return the parent
     */
    public GroupModel getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(GroupModel parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public List<GroupModel> getChildren() {
        return children;
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

    /**
     * @return the groupRoles
     */
    public List<GroupRoleModel> getGroupRoles() {
        return groupRoles;
    }
    
}
