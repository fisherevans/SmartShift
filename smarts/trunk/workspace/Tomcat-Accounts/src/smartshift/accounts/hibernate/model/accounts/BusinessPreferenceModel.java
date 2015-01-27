package smartshift.accounts.hibernate.model.accounts;

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
@Table(name = "UserPreference")
public class BusinessPreferenceModel implements Serializable {
    private static final long serialVersionUID = 1738459159424484410L;

    @Id
    @ManyToOne
    @JoinColumn(name = "busID", nullable = false)
    private BusinessModel business;

    @Id
    @ManyToOne
    @JoinColumn(name = "prefID", nullable = false)
    private PreferenceModel preference;

    @Column(name = "prefVal", length = 256)
    private String value;

    public BusinessPreferenceModel() {
    }

    public BusinessModel getBusiness() {
        return business;
    }

    public void setBusiness(BusinessModel business) {
        this.business = business;
    }

    public PreferenceModel getPreference() {
        return preference;
    }

    public void setPreference(PreferenceModel preference) {
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
        if(obj instanceof BusinessPreferenceModel) {
            BusinessPreferenceModel other = (BusinessPreferenceModel) obj;
            return other.getBusiness().getId() == getBusiness().getId() && other.getPreference().getId() == getPreference().getId();
        }
        return false;
    }
}
