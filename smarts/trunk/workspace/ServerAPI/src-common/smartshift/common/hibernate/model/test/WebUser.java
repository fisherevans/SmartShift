package smartshift.common.hibernate.model.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fevans
 * @version Sept 18, 2014
 */
@Entity
@Table(name = "webuser")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WebUser {
    @Id
    @Column(name = "username")
    @Expose
    @SerializedName("username")
    private String username;

    @Column(name = "email")
    @Expose
    @SerializedName("email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
