package smartshift.accounts.hibernate.model;

import java.util.Date;
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
@Table(name = "Build")
public class BuildModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "version", nullable = false, length = 20)
    private String version;

    @Column(name = "createTS", nullable = false)
    private Date createTimestamp;

    @Column(name = "sqlDir", nullable = false, length = 256)
    private String sqlDirectory;
    
    @OneToMany(mappedBy = "build")
    private List<BusinessModel> businesses;

    /**
     * Initializes the object.
     */
    public BuildModel() {
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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
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
     * @return the sqlDirectory
     */
    public String getSqlDirectory() {
        return sqlDirectory;
    }

    /**
     * @param sqlDirectory the sqlDirectory to set
     */
    public void setSqlDirectory(String sqlDirectory) {
        this.sqlDirectory = sqlDirectory;
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
}
