package smartshift.business.hibernate.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "Schedule")
public class ScheduleModel {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "startDate")
    private Date startDate;
    
    @Column(name = "schedTempVersionID", nullable = false)
    private Integer scheduleTemplateVersionID;

    /**
     * Initializes the object.
     */
    public ScheduleModel() {
        
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * @return the scheduleTemplateVersionID
     */
    public Integer getScheduleTemplateVersionID() {
        return scheduleTemplateVersionID;
    }
    
    /**
     * @param scheduleTemplateVersionID the id to set
     */
    public void setScheduleTemplateVersionID(Integer scheduleTemplateVersionID) {
        this.scheduleTemplateVersionID = scheduleTemplateVersionID;
    }
}
