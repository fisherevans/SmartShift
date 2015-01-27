package smartshift.accounts.hibernate.model.accounts;

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
import smartshift.common.util.collections.ROList;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "busID", nullable = false)
    private BusinessModel business;
    
    @Column(name = "empID", nullable = false)
    private Integer employeeID;

    @Column(name = "joinTS", nullable = false)
    private Date joinTimestamp;
    
    @OneToMany(mappedBy = "userBusinessEmployee")
    private List<UserBusinessEmployeePreferenceModel> userBusinessEmployeePreferences;

    public UserBusinessEmployeeModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public BusinessModel getBusiness() {
        return business;
    }

    public void setBusiness(BusinessModel business) {
        this.business = business;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Date getJoinTimestamp() {
        return joinTimestamp;
    }

    public void setJoinTimestamp(Date joinTimestamp) {
        this.joinTimestamp = joinTimestamp;
    }

    public ROList<UserBusinessEmployeePreferenceModel> getUserBusinessEmployeePreferences() {
        return new ROList<UserBusinessEmployeePreferenceModel>(userBusinessEmployeePreferences);
    }

    @Override
    public int hashCode() {
        return user.getId().hashCode()*business.getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserBusinessEmployeeModel) {
            UserBusinessEmployeeModel other = (UserBusinessEmployeeModel) obj;
            return other.getUser().getId() == user.getId() && other.getBusiness().getId() == business.getId();
        }
        return false;
    }
}
