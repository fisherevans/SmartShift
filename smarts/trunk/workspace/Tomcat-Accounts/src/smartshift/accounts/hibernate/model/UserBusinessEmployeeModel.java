package smartshift.accounts.hibernate.model;

import java.io.Serializable;
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
@Table(name = "UserBusinessEmployee")
public class UserBusinessEmployeeModel implements Serializable {
    private static final long serialVersionUID = 9019038361957129848L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userID", nullable = false)
    private Integer userID;

    @Column(name = "busID", nullable = false)
    private Integer businessID;
    
    @Column(name = "empID", nullable = false)
    private Integer employeeID;

    @Column(name = "joinTS", nullable = false)
    private Date joinTimestamp;

    /**
     * Initializes the object.
     */
    public UserBusinessEmployeeModel() {
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
     * @return the userID
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
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
     * @return the joinTimestamp
     */
    public Date getJoinTimestamp() {
        return joinTimestamp;
    }

    /**
     * @param joinTimestamp the joinTimestamp to set
     */
    public void setJoinTimestamp(Date joinTimestamp) {
        this.joinTimestamp = joinTimestamp;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return userID.hashCode()*businessID.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserBusinessEmployeeModel) {
            UserBusinessEmployeeModel other = (UserBusinessEmployeeModel) obj;
            return other.getUserID() == getUserID() && other.getBusinessID() == getBusinessID();
        }
        return false;
    }
}
