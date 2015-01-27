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
import com.google.gson.annotations.Expose;

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

    public UserContactMethodModel() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ContactMethodModel getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(ContactMethodModel contactMethod) {
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
        if(obj instanceof UserContactMethodModel) {
            UserContactMethodModel other = (UserContactMethodModel) obj;
            return other.getUser().getId() == getUser().getId() && other.getContactMethod().getId() == getContactMethod().getId();
        }
        return false;
    }
    
    public static class GsonObject {
        @Expose
        Integer methodID;
        @Expose
        String methodName;
        @Expose
        String value;
    }
}
