package smartshift.common.hibernate.model.accounts;

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
@Table(name = "ApplicationTip", schema = "Accounts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
public class ApplicationTip {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "minBuildID", nullable = false)
    private Build minBuild;

    @ManyToOne
    @JoinColumn(name = "maxBuildID", nullable = false)
    private Build maxBuild;

    @Column(name = "tip", nullable = false, length = 256)
    private String tip;

    public ApplicationTip() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Build getMinBuild() {
        return minBuild;
    }

    public void setMinBuild(Build minBuild) {
        this.minBuild = minBuild;
    }

    public Build getMaxBuild() {
        return maxBuild;
    }

    public void setMaxBuild(Build maxBuild) {
        this.maxBuild = maxBuild;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
