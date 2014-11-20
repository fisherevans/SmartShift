package smartshift.common.hibernate.model.accounts;

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
@Table(name = "User", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class User {
    @Expose
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Expose
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "passHash", nullable = false, length = 256)
    private String passHash;

    @Expose
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "createTS", nullable = false)
    private Date createTimestamp = new Date();

    @Expose
    @ManyToOne
    @JoinColumn(name = "imgID")
    private Image image;

    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    @Column(name = "flags", nullable = false)
    private Integer flags = 0;
    
    @OneToMany(mappedBy = "user")
    private List<UserContactMethod> userContactMethods;
    
    @OneToMany(mappedBy = "user")
    private List<UserBusinessEmployee> userBusinessEmployees;

    public User() {
    }

    public User(String username, String passHash, String email) {
        this.username = username;
        this.passHash = passHash;
        this.email = email;
    }
    
    public List<UserContactMethod> getUserContactMethods() {
        return userContactMethods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public List<UserBusinessEmployee> getUserBusinessEmployees() {
        return userBusinessEmployees;
    }
}
