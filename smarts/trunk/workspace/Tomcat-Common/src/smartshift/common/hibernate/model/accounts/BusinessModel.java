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
@Table(name = "Business")
public class BusinessModel {
    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Expose
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "description", nullable = true, length = 512)
    private String description;
    
    @Column(name = "groupID")
    private Integer groupID;

    @ManyToOne
    @JoinColumn(name = "buildID", nullable = false)
    private BuildModel build;

    @ManyToOne
    @JoinColumn(name = "servID", nullable = false)
    private ServerModel server;

    @Expose
    @ManyToOne
    @JoinColumn(name = "imgID")
    private ImageModel image;

    @Expose
    @ManyToOne
    @JoinColumn(name = "addressID")
    private AddressModel address;

    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    @Column(name = "flags", nullable = false)
    private Integer flags = 0;
    
    @ManyToMany
    @JoinTable(
        name="UserBusinessEmployee",
        joinColumns={@JoinColumn(name="busID", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="userID", referencedColumnName="id")})
    private List<UserModel> users;
    
    @OneToMany(mappedBy = "business")
    private List<BusinessPreferenceModel> businessPreferences;

    public BusinessModel() {
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public BuildModel getBuild() {
        return build;
    }

    public void setBuild(BuildModel build) {
        this.build = build;
    }

    public ServerModel getServer() {
        return server;
    }

    public void setServer(ServerModel server) {
        this.server = server;
    }

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
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

    public ROList<UserModel> getUsers() {
        return new ROList<UserModel>(users);
    }
}
