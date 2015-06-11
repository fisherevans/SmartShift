package smartshift.accounts.hibernate.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author fevans
 * @version Oct 26, 2014
 */
@Entity
@Table(name = "Server")
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

    /**
     * Initializes the object.
     */
    public ServerModel() {
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
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the businesses
     */
    public List<BusinessModel> getBusinesses() {
        return businesses;
    }

    /**
     * @param businesses the businesses to set
     */
    public void setBusinesses(List<BusinessModel> businesses) {
        this.businesses = businesses;
    }

    /**
     * @return the systemProperties
     */
    public List<SystemPropertyModel> getSystemProperties() {
        return systemProperties;
    }

    /**
     * @param systemProperties the systemProperties to set
     */
    public void setSystemProperties(List<SystemPropertyModel> systemProperties) {
        this.systemProperties = systemProperties;
    }
}
