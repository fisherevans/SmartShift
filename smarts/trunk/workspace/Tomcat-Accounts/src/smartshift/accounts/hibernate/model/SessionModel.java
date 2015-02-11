package smartshift.accounts.hibernate.model;

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
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Session")
//@NamedQueries({
//    @NamedQuery(name = SessionModel.GET_ACTIVE_SESSIONS,
//                query = "select s from SessionModel s "
//                      + "where s.userBusinessEmployeeID in (select id "
//                      +                "from UserBusinessEmployeeModel ube "
//                      +                "where ube.businessID = :" + SessionModel.GET_ACTIVE_SESSIONS_BUSINESS_ID
//                      + ") and s.lastActivityTimestamp >= :" + SessionModel.GET_ACTIVE_SESSIONS_LAST_ACCESS
//    )
//})
public class SessionModel {
    public static final String GET_ACTIVE_SESSIONS = "getActiveSessions";

    public static final String GET_ACTIVE_SESSIONS_BUSINESS_ID = "businessID";

    public static final String GET_ACTIVE_SESSIONS_LAST_ACCESS = "lastAccess";
    
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
