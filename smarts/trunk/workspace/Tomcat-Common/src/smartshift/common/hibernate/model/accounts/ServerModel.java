package smartshift.common.hibernate.model.accounts;

import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import smartshift.common.util.collections.ROList;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Server")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class ServerModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "hostname", nullable = false, length = 256)
    private String hostname;

    @Column(name = "ipAddr", nullable = false, length = 46)
    private String ipAddress;
    
    @OneToMany(mappedBy = "server")
    private List<BusinessModel> businesses;
    
    @OneToMany(mappedBy = "server")
    private List<SystemPropertyModel> systemProperties;

    public ServerModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public ROList<BusinessModel> getBusinesses() {
        return new ROList<BusinessModel>(businesses);
    }
    
    public ROList<SystemPropertyModel> getSystemProperties() {
        return new ROList<SystemPropertyModel>(systemProperties);
    }
}
