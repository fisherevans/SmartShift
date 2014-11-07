package smartshift.common.hibernate.model.accounts;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "UserBusiness", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class UserBusiness implements Serializable {
    private static final long serialVersionUID = 9019038361957129848L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "busID", nullable = false)
    private User business;

    @Column(name = "joinTS", nullable = false)
    private Date joinTimestamp;
    
    @OneToMany(mappedBy = "userBusiness")
    private List<UserBusinessPreference> userBusinessPreferences;

    public UserBusiness() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getBusiness() {
        return business;
    }

    public void setBusiness(User business) {
        this.business = business;
    }

    public Date getJoinTimestamp() {
        return joinTimestamp;
    }

    public void setJoinTimestamp(Date joinTimestamp) {
        this.joinTimestamp = joinTimestamp;
    }

    @Override
    public int hashCode() {
        return user.getId().hashCode()*business.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserBusiness) {
            UserBusiness other = (UserBusiness) obj;
            return other.getUser().getId() == user.getId() && other.getBusiness().getId() == business.getId();
        }
        return false;
    }
}
