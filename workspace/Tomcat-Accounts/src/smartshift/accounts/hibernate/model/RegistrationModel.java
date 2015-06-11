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
@Table(name = "Registration")
public class RegistrationModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "employeeID", nullable = false)
    private Integer employeeID;
    
    @Column(name = "businessID", nullable = false)
    private Integer businessID;
    
    @Column(name = "nextID", nullable = false)
    private Integer nextID;
    
    @Column(name = "verificationCode", nullable = false, length = 256)
    private String verificationCode;
    
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "createTS", nullable = false)
    private Date createTimestamp = new Date();

    /**
     * Initializes the object.
     */
    public RegistrationModel() {
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
     * @return the employeeID
     */
    public Integer getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * @return the businessID
     */
    public Integer getBusinessID() {
        return businessID;
    }

    /**
     * @param businessID the businessID to set
     */
    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    /**
     * @return the nextID
     */
    public Integer getNextID() {
        return nextID;
    }

    /**
     * @param nextID the nextID to set
     */
    public void setNextID(Integer nextID) {
        this.nextID = nextID;
    }

    /**
     * @return the verificationCode
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    /**
     * @param verificationCode the verificationCode to set
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
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
}
