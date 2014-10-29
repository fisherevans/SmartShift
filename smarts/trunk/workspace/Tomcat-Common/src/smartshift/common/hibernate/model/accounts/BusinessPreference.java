package smartshift.common.hibernate.model.accounts;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "UserPreference", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class BusinessPreference implements Serializable {
    private static final long serialVersionUID = 1738459159424484410L;

    @Id
    @ManyToOne
    @JoinColumn(name = "busID", nullable = false)
    private Business business;

    @Id
    @ManyToOne
    @JoinColumn(name = "prefID", nullable = false)
    private Preference preference;

    @Column(name = "prefVal", length = 256)
    private String value;

    public BusinessPreference() {
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return business.getId().hashCode()*preference.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BusinessPreference) {
            BusinessPreference other = (BusinessPreference) obj;
            return other.getBusiness().getId() == getBusiness().getId() && other.getPreference().getId() == getPreference().getId();
        }
        return false;
    }
}
