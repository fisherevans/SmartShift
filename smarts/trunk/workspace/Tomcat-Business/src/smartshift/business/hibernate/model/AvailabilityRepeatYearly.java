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
@Table(name = "AvailRepeatYearly")
public class AvailabilityRepeatYearly {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "availID", nullable = false)
    private Integer availabilityID;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "dayOfMonth", nullable = false)
    private Integer dayOfMonth;
    
    /**
     * Initializes the object.
     */
    public AvailabilityRepeatYearly() {
        
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
     * @return the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return the dayOfMonth
     */
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * @param dayOfMonth the dayOfMonth to set
     */
    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
