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
@Table(name = "UserBusinessPreference", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class UserBusinessPreference implements Serializable {
    private static final long serialVersionUID = -3072730232222834616L;


    @Id
    @ManyToOne
    @JoinColumn(name = "userBusID", nullable = false)
    private UserBusiness userBusiness;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "prefID", nullable = false)
    private Preference preference;

    @Column(name = "prefVal", length = 256)
    private String value;

    public UserBusinessPreference() {
    }

    public UserBusiness getUserBusiness() {
        return userBusiness;
    }

    public void setUserBusiness(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
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
        return userBusiness.getId().hashCode()*preference.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserBusinessPreference) {
            UserBusinessPreference other = (UserBusinessPreference) obj;
            return other.getUserBusiness().getId() == getUserBusiness().getId() && other.getPreference().getId() == getPreference().getId();
        }
        return false;
    }
}
