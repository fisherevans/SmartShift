package smartshift.accounts.hibernate.model;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.EntityResult;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import smartshift.accounts.hibernate.model.custom.GetActiveSessionsModel;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Session")
@NamedNativeQueries({
    @NamedNativeQuery(name = SessionModel.GET_ACTIVE_SESSIONS,
                     query = "select s.id, u.username, s.sessionKey, ube.busID as businessID, ube.empID as employeeID, s.lastActivityTS as lastActivity"
                           + "  from Session s"
                           + "  left join UserBusinessEmployee ube on s.userBusEmpId = ube.id"
                           + "  left join User u on ube.userID = u.id"
                           + " where ube.busID = ?"
                           + "   and s.lastActivityTS >= ?",
               resultClass = GetActiveSessionsModel.class
    )
})
public class SessionModel {
    public static final String GET_ACTIVE_SESSIONS = "getActiveSessions";

    public static final Integer GET_ACTIVE_SESSIONS_BUSINESS_ID = 0;

    public static final Integer GET_ACTIVE_SESSIONS_LAST_ACCESS = 1;
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userBusEmpID", nullable = false)
    private Integer userBusinessEmployeeID;

    @Column(name = "sessionKey", nullable = false, length = 256)
    private String sessionKey;
    
    @Column(name = "lastActivityTS", nullable = false)
    private Date lastActivityTimestamp = new Date();

    public SessionModel() {
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
     * @return the userBusinessEmployeeID
     */
    public Integer getUserBusinessEmployeeID() {
        return userBusinessEmployeeID;
    }

    /**
     * @param userBusinessEmployeeID the userBusinessEmployeeID to set
     */
    public void setUserBusinessEmployeeID(Integer userBusinessEmployeeID) {
        this.userBusinessEmployeeID = userBusinessEmployeeID;
    }

    /**
     * @return the sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @param sessionKey the sessionKey to set
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @return the lastActivityTimestamp
     */
    public Date getLastActivityTimestamp() {
        return lastActivityTimestamp;
    }

    /**
     * @param lastActivityTimestamp the lastActivityTimestamp to set
     */
    public void setLastActivityTimestamp(Date lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }
}
