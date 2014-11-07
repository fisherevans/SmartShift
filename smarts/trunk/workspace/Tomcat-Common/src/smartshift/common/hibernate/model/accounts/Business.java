package smartshift.common.hibernate.model.accounts;

import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.google.gson.annotations.Expose;
import smartshift.common.util.collections.ROList;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Business", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class Business {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @ManyToOne
    @JoinColumn(name = "buildID", nullable = false)
    private Build build;

    @ManyToOne
    @JoinColumn(name = "servID", nullable = false)
    private Server server;

    @ManyToOne
    @JoinColumn(name = "imgID")
    private Image image;

    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    @Column(name = "flags", nullable = false)
    private Integer flags = 0;
    
    @ManyToMany
    @JoinTable(
        name="UserBusiness",
        joinColumns={@JoinColumn(name="busID", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="userID", referencedColumnName="id")})
    private List<User> users;
    
    @OneToMany(mappedBy = "business")
    private List<BusinessPreference> businessPreferences;

    public Business() {
    }
    
    public GsonObject getGsonObject() {
        GsonObject obj = new GsonObject();
        obj.id = id;
        obj.name = name;
        obj.image = image == null ? null : image.getGsonObject();
        return obj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
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

    public ROList<User> getUsers() {
        return new ROList<User>(users);
    }
    
    public static class GsonObject {
        @Expose
        public Integer id;
        @Expose
        public String name;
        @Expose
        public Image.GsonObject image;
    }
}
