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
@Table(name = "Availability")
public class Availability {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "templateID", nullable = false)
    private Integer templateID;

    @Column(name = "start", nullable = false)
    private Integer start;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "repeatEvery", nullable = false)
    private Integer repeatEvery;

    @Column(name = "repeatCount", nullable = false)
    private Integer repeatCount;

    @Column(name = "repeaseOffset")
    private Integer repeaseOffset;

    @Column(name = "unavailable", nullable = false)
    private Boolean unavailable;
    
    /**
     * Initializes the object.
     */
    public Availability() {
        
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

    /**
     * @return the repeatEvery
     */
    public Integer getRepeatEvery() {
        return repeatEvery;
    }

    /**
     * @param repeatEvery the repeatEvery to set
     */
    public void setRepeatEvery(Integer repeatEvery) {
        this.repeatEvery = repeatEvery;
    }

    /**
     * @return the repeatCount
     */
    public Integer getRepeatCount() {
        return repeatCount;
    }

    /**
     * @param repeatCount the repeatCount to set
     */
    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    /**
     * @return the repeaseOffset
     */
    public Integer getRepeaseOffset() {
        return repeaseOffset;
    }

    /**
     * @param repeaseOffset the repeaseOffset to set
     */
    public void setRepeaseOffset(Integer repeaseOffset) {
        this.repeaseOffset = repeaseOffset;
    }

    /**
     * @return the unavailable
     */
    public Boolean getUnavailable() {
        return unavailable;
    }

    /**
     * @param unavailable the unavailable to set
     */
    public void setUnavailable(Boolean unavailable) {
        this.unavailable = unavailable;
    }
}
