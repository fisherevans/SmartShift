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
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "uri", nullable = false, length = 256)
    private String uri;

    @Column(name = "alt", nullable = false, length = 256)
    private String alternativeText;

    @OneToMany(mappedBy = "image")
    private List<Business> businesses;

    @OneToMany(mappedBy = "image")
    private List<User> users;

    public Image() {
    }
    
    public GsonObject getGsonObject() {
        GsonObject obj = new GsonObject();
        obj.id = id;
        obj.alternativeText = alternativeText;
        return obj;
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
    
    public static class GsonObject {
        @Expose
        public Integer id;
        @Expose
        public String alternativeText;
    }
}
