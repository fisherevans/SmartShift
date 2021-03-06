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
@Table(name = "AvailInstance")
public class AvailabilityInstanceModel{
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "templateID", nullable = false)
    private Integer templateID;
    
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    
    /**
     * Initializes the object.
     */
    public AvailabilityInstanceModel() {
        
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
     * @return the templateID
     */
    public Integer getTemplateID() {
        return templateID;
    }

    /**
     * @param templateID the templateID to set
     */
    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
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
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
