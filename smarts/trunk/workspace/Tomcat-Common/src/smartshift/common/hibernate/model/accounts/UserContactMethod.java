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
@Table(name = "UserContactMethod", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class UserContactMethod implements Serializable {
    private static final long serialVersionUID = -1657900056377969311L;

    @Id
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "cMethodID", nullable = false)
    private ContactMethod contactMethod;

    @Column(name = "cMethodVal", length = 60)
    private String contactMethodValue;

    public UserContactMethod() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getContactMethodValue() {
        return contactMethodValue;
    }

    public void setContactMethodValue(String contactMethodValue) {
        this.contactMethodValue = contactMethodValue;
    }

    @Override
    public int hashCode() {
        return user.getId().hashCode()*contactMethod.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserContactMethod) {
            UserContactMethod other = (UserContactMethod) obj;
            return other.getUser().getId() == getUser().getId() && other.getContactMethod().getId() == getContactMethod().getId();
        }
        return false;
    }
}
