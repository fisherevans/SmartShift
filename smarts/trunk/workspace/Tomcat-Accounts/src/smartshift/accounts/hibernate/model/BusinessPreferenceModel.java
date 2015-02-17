package smartshift.accounts.hibernate.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    /**
     * Initializes the object.
     */
    public BusinessPreferenceModel() {
    }

    /**
     * @return the business
     */
    public BusinessModel getBusiness() {
        return business;
    }

    /**
     * @param business the business to set
     */
    public void setBusiness(BusinessModel business) {
        this.business = business;
    }

    /**
     * @return the preference
     */
    public PreferenceModel getPreference() {
        return preference;
    }

    /**
     * @param preference the preference to set
     */
    public void setPreference(PreferenceModel preference) {
        this.preference = preference;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return business.getId().hashCode()*preference.getId().hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BusinessPreferenceModel) {
            BusinessPreferenceModel other = (BusinessPreferenceModel) obj;
            return other.getBusiness().getId() == getBusiness().getId() && other.getPreference().getId() == getPreference().getId();
        }
        return false;
    }
}
