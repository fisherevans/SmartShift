package smartshift.common.hibernate.model.accounts;

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
@Table(name = "Image", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class Image {
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
    private List<Business> businesses;

    @OneToMany(mappedBy = "image")
    private List<User> users;

    public Image() {
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
    
    public ROList<Business> getBusinesses() {
        return new ROList<Business>(businesses);
    }
    
    public ROList<User> getUsers() {
        return new ROList<User>(users);
    }
}
