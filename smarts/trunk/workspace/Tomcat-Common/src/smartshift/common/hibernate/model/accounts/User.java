package smartshift.common.hibernate.model.accounts;

import java.util.Date;
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
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "passHash", nullable = false, length = 256)
    private String passHash;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "createTS", nullable = false)
    private Date createTimestamp = new Date();
    
    @ManyToOne
    @JoinColumn(name = "imgID")
    private Image image;

    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    @Column(name = "flags", nullable = false)
    private Integer flags = 0;

    @ElementCollection
    @CollectionTable(name = "UserContactMethod", joinColumns = @JoinColumn(name = "userID"))
    @MapKeyJoinColumn(name = "cMethodID")
    private Map<ContactMethod, UserContactMethodRelationData> contactMethods;
    
    @ManyToMany(mappedBy="users")
    private List<Business> businesses;
    
    @OneToMany(mappedBy = "user")
    private List<UserBusiness> userBusinesses;

    public User() {
    }
    
    public User(AddRequest request) {
        username = request.username;
        email = request.email;
        passHash = BCrypt.hashpw(request.password, BCrypt.gensalt());
    }

    public User(String username, String passHash, String email) {
        this.username = username;
        this.passHash = passHash;
        this.email = email;
    }

    public GsonObject getGsonObject() {
        GsonObject obj = new GsonObject();
        obj.id = id;
        obj.username = username;
        return obj;
    }

    public MultivaluedMap<Integer, UserContactMethod.GsonObject> getUserContactMethodsGsonObjectMap() {
        MultivaluedMap<Integer, UserContactMethod.GsonObject> map = new MultivaluedHashMap<>();
        for(ContactMethod method:contactMethods.keySet()) {
            UserContactMethod.GsonObject obj = new UserContactMethod.GsonObject();
            obj.methodID = method.getId();
            obj.methodName = method.getName();
            obj.value = contactMethods.get(method).getValue();
            map.add(method.getId(), obj);
        }
        return map;
    }

    public UserContactMethod.GsonObject getUserContactMethodsGsonObject(Integer methodID) {
        for(ContactMethod method:contactMethods.keySet()) {
            if(method.getId().equals(methodID)) {
                UserContactMethod.GsonObject obj = new UserContactMethod.GsonObject();
                obj.methodID = method.getId();
                obj.methodName = method.getName();
                obj.value = contactMethods.get(method).getValue();
                return obj;
            }
        }
        return null;
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
    
    public ROList<Business> getBusineses() {
        return new ROList<Business>(businesses);
    }
    
    public static class AddRequest {
        @Expose
        String username;
        @Expose
        String email;
        @Expose
        String password;
    }
    
    public static class GsonObject {
        @Expose
        Integer id;
        @Expose
        String username;
    }
}
