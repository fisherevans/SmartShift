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
@Table(name = "UserContactMethod")
public class UserContactMethodModel implements Serializable {
    private static final long serialVersionUID = -1657900056377969311L;

    @Id
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserModel user;

    @Id
    @ManyToOne
    @JoinColumn(name = "cMethodID", nullable = false)
    private ContactMethodModel contactMethod;

    @Column(name = "cMethodVal", length = 60)
    private String contactMethodValue;

    /**
     * Initializes the object.
     */
    public UserContactMethodModel() {
    }

    /**
     * @return the user
     */
    public UserModel getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserModel user) {
        this.user = user;
    }

    /**
     * @return the contactMethod
     */
    public ContactMethodModel getContactMethod() {
        return contactMethod;
    }

    /**
     * @param contactMethod the contactMethod to set
     */
    public void setContactMethod(ContactMethodModel contactMethod) {
        this.contactMethod = contactMethod;
    }

    /**
     * @return the contactMethodValue
     */
    public String getContactMethodValue() {
        return contactMethodValue;
    }

    /**
     * @param contactMethodValue the contactMethodValue to set
     */
    public void setContactMethodValue(String contactMethodValue) {
        this.contactMethodValue = contactMethodValue;
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
        return user.getId().hashCode()*contactMethod.getId().hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserContactMethodModel) {
            UserContactMethodModel other = (UserContactMethodModel) obj;
            return other.getUser().getId() == getUser().getId() && other.getContactMethod().getId() == getContactMethod().getId();
        }
        return false;
    }
}
