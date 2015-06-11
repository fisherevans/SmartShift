package smartshift.accounts.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    /**
     * Initializes the object.
     */
    public ApplicationTipModel() {
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
     * @return the minBuild
     */
    public BuildModel getMinBuild() {
        return minBuild;
    }

    /**
     * @param minBuild the minBuild to set
     */
    public void setMinBuild(BuildModel minBuild) {
        this.minBuild = minBuild;
    }

    /**
     * @return the maxBuild
     */
    public BuildModel getMaxBuild() {
        return maxBuild;
    }

    /**
     * @param maxBuild the maxBuild to set
     */
    public void setMaxBuild(BuildModel maxBuild) {
        this.maxBuild = maxBuild;
    }

    /**
     * @return the tip
     */
    public String getTip() {
        return tip;
    }

    /**
     * @param tip the tip to set
     */
    public void setTip(String tip) {
        this.tip = tip;
    }
}
