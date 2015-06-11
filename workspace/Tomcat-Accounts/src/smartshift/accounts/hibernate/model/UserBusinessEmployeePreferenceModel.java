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

    /**
     * Initializes the object.
     */
    public UserBusinessEmployeePreferenceModel() {
    }

    /**
     * @return the userBusinessEmployee
     */
    public UserBusinessEmployeeModel getUserBusinessEmployee() {
        return userBusinessEmployee;
    }

    /**
     * @param userBusinessEmployee the userBusinessEmployee to set
     */
    public void setUserBusinessEmployee(UserBusinessEmployeeModel userBusinessEmployee) {
        this.userBusinessEmployee = userBusinessEmployee;
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
        return userBusinessEmployee.getId().hashCode() * preference.getId().hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserBusinessEmployeePreferenceModel) {
            UserBusinessEmployeePreferenceModel other = (UserBusinessEmployeePreferenceModel) obj;
            return other.getUserBusinessEmployee().getId() == getUserBusinessEmployee().getId() && other.getPreference().getId() == getPreference().getId();
        }
        return false;
    }
}
