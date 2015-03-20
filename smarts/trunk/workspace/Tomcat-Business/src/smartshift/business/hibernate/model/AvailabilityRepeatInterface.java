package smartshift.business.hibernate.model;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * A simple polymorphism to handle the different repeat types genericly.
 */
public interface AvailabilityRepeatInterface {
    
    /**
     * @return the availavility id for the repeat
     */
    public Integer getAvailabilityID();
}
