package smartshift.accounts.hibernate.model.accounts;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "Session")
public class SessionModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userBusEmpID", nullable = false)
    private UserBusinessEmployeeModel userBusinessEmployee;

    @Column(name = "sessionKey", nullable = false, length = 256)
    private String sessionKey;
    
    @Column(name = "lastActivityTS", nullable = false)
    private Date lastActivityTimestamp = new Date();

    public SessionModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserBusinessEmployeeModel getUserBusinessEmployee() {
        return userBusinessEmployee;
    }

    public void setUserBusinessEmployee(UserBusinessEmployeeModel userBusinessEmployee) {
        this.userBusinessEmployee = userBusinessEmployee;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    public void setLastActivityTimestamp(Date lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
