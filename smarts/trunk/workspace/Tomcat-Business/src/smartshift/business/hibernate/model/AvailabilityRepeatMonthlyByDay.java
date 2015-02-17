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
@Table(name = "AvailRepeatMonthlyByDay")
public class AvailabilityRepeatMonthlyByDay {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "availID", nullable = false)
    private Integer availabilityID;

    @Column(name = "dayOfWeek", nullable = false)
    private Integer dayOfWeek;
    
    /**
     * Initializes the object.
     */
    public AvailabilityRepeatMonthlyByDay() {
        
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
     * @return the availabilityID
     */
    public Integer getAvailabilityID() {
        return availabilityID;
    }

    /**
     * @param availabilityID the availabilityID to set
     */
    public void setAvailabilityID(Integer availabilityID) {
        this.availabilityID = availabilityID;
    }

    /**
     * @return the dayOfWeek
     */
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * @param dayOfWeek the dayOfWeek to set
     */
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
