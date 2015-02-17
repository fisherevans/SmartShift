package smartshift.accounts.hibernate.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Image")
public class ImageModel {
    @Expose
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Expose
    @Column(name = "uri", nullable = false, length = 256)
    private String uri;

    @Expose
    @Column(name = "alt", nullable = false, length = 256)
    private String alternativeText;

    @OneToMany(mappedBy = "image")
    private List<BusinessModel> businesses;

    @OneToMany(mappedBy = "image")
    private List<UserModel> users;

    /**
     * Initializes the object.
     */
    public ImageModel() {
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
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the alternativeText
     */
    public String getAlternativeText() {
        return alternativeText;
    }

    /**
     * @param alternativeText the alternativeText to set
     */
    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }

    /**
     * @return the businesses
     */
    public List<BusinessModel> getBusinesses() {
        return businesses;
    }

    /**
     * @param businesses the businesses to set
     */
    public void setBusinesses(List<BusinessModel> businesses) {
        this.businesses = businesses;
    }

    /**
     * @return the users
     */
    public List<UserModel> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
