package smartshift.business.hibernate.model;

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
import smartshift.common.security.Authentication;
import smartshift.common.util.collections.ROList;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Role")
public class RoleModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;
    
    @OneToMany(mappedBy = "role")
    private List<GroupRoleModel> groupRoles;

    public RoleModel() {
    }

    public RoleModel(String name) {
        super();
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

    /**
     * @return the groupRoles
     */
    public List<GroupRoleModel> getGroupRoles() {
        return groupRoles;
    }
    
}
