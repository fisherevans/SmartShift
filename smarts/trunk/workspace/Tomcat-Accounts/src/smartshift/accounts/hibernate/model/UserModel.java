package smartshift.accounts.hibernate.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "User")
public class UserModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 24)
    private String username;

    @Column(name = "passHash", nullable = false, length = 256)
    private String passHash;

    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "createTS", nullable = false)
    private Date createTimestamp = new Date();

    @Column(name = "imgID", nullable = false)
    private Integer imageID;

    @Column(name = "inactive", nullable = false)
    private Boolean inactive = false;

    @Column(name = "flags", nullable = false)
    private Integer flags = 0;

    /**
     * Initializes the object.
     */
    public UserModel() {
    }

    /**
     * Initializes the object.
     * @param username
     * @param passHash
     * @param email
     */
    public UserModel(String username, String passHash, String email) {
        this.username = username;
        this.passHash = passHash;
        this.email = email;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the passHash
     */
    public String getPassHash() {
        return passHash;
    }

    /**
     * @param passHash the passHash to set
     */
    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the createTimestamp
     */
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * @param createTimestamp the createTimestamp to set
     */
    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * @return the imageID
     */
    public Integer getImageID() {
        return imageID;
    }

    /**
     * @param imageID the imageID to set
     */
    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    /**
     * @return the inactive
     */
    public Boolean getInactive() {
        return inactive;
    }

    /**
     * @param inactive the inactive to set
     */
    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    /**
     * @return the flags
     */
    public Integer getFlags() {
        return flags;
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(Integer flags) {
        this.flags = flags;
    }
}
