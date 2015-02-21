package smartshift.business.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "Shift")
public class ShiftModel {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dayOfWeek", nullable = false)
    private Integer templateID;

    @Column(name = "start", nullable = false)
    private Integer start;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * Initializes the object.
     */
    public ShiftModel() {
        
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
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}