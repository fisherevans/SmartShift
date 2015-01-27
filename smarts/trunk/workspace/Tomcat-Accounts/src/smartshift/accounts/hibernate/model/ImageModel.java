package smartshift.accounts.hibernate.model;

import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    public ImageModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }
    
    public ROList<BusinessModel> getBusinesses() {
        return new ROList<BusinessModel>(businesses);
    }
    
    public ROList<UserModel> getUsers() {
        return new ROList<UserModel>(users);
    }
}
