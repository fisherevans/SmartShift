package smartshift.common.hibernate.model.accounts;

import java.util.Date;
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
@Table(name = "Build")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
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


    public BuildModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getSqlDirectory() {
        return sqlDirectory;
    }

    public void setSqlDirectory(String sqlDirectory) {
        this.sqlDirectory = sqlDirectory;
    }
    
    public ROList<BusinessModel> getBusinesses() {
        return new ROList<BusinessModel>(businesses);
    }
}
