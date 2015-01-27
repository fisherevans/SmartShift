package smartshift.accounts.hibernate.model;

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
@Table(name = "ApplicationTip")
public class ApplicationTipModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "minBuildID", nullable = false)
    private BuildModel minBuild;

    @ManyToOne
    @JoinColumn(name = "maxBuildID", nullable = false)
    private BuildModel maxBuild;

    @Column(name = "tip", nullable = false, length = 256)
    private String tip;

    public ApplicationTipModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BuildModel getMinBuild() {
        return minBuild;
    }

    public void setMinBuild(BuildModel minBuild) {
        this.minBuild = minBuild;
    }

    public BuildModel getMaxBuild() {
        return maxBuild;
    }

    public void setMaxBuild(BuildModel maxBuild) {
        this.maxBuild = maxBuild;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
