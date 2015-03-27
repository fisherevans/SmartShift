package smartshift.business.hibernate.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "SchedTemplateVersion")
@NamedQueries({
    @NamedQuery(name = ScheduleTemplateVersionModel.GET_MAX_TEMPLATE_ID,
                query = "select max(stv.scheduleTemplateID) "
                      + "  from ScheduleTemplateVersionModel as stv"
    )
})
public class ScheduleTemplateVersionModel{
    /**
     * named query to get the max id of all schedule template ids
     */
    public static final String GET_MAX_TEMPLATE_ID = "getMaxScheduleTemplateId";
    
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "schedTemplateID", nullable = false)
    private Integer scheduleTemplateID;

    @Column(name = "name", length = 60)
    private String name;

    @Column(name = "createTS", nullable = false)
    private Date createTimestamp;

    @Column(name = "version", nullable = false)
    private Integer version;

    /**
     * Initializes the object.
     */
    public ScheduleTemplateVersionModel() {
        
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
     * @return the scheduleTemplateID
     */
    public Integer getScheduleTemplateID() {
        return scheduleTemplateID;
    }

    /**
     * @param scheduleTemplateID the scheduleTemplateID to set
     */
    public void setScheduleTemplateID(Integer scheduleTemplateID) {
        this.scheduleTemplateID = scheduleTemplateID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
