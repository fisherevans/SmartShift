package smartshift.accounts.hibernate.model;

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
@Table(name = "UserBusinessEmployeePreference")
public class UserBusinessEmployeePreferenceModel implements Serializable {
    private static final long serialVersionUID = -3072730232222834616L;

    @Id
    @ManyToOne
    @JoinColumn(name = "userBusEmpID", nullable = false)
    private UserBusinessEmployeeModel userBusinessEmployee;

    @Id
    @ManyToOne
    @JoinColumn(name = "prefID", nullable = false)
    private PreferenceModel preference;

    @Column(name = "prefVal", length = 256)
    private String value;

    public UserBusinessEmployeePreferenceModel() {
    }

    public UserBusinessEmployeeModel getUserBusinessEmployee() {
        return userBusinessEmployee;
    }

    public void setUserBusinessEmployee(UserBusinessEmployeeModel userBusiness) {
        this.userBusinessEmployee = userBusiness;
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
        return userBusinessEmployee.getId().hashCode() * preference.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserBusinessEmployeePreferenceModel) {
            UserBusinessEmployeePreferenceModel other = (UserBusinessEmployeePreferenceModel) obj;
            return other.getUserBusinessEmployee().getId() == getUserBusinessEmployee().getId() && other.getPreference().getId() == getPreference().getId();
        }
        return false;
    }
}
