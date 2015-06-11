package smartshift.business.hibernate.model;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * A simple polymorphism to handle the different repeat types genericly.
 */
public interface AvailabilityRepeatInterface {
    
    /**
     * @return the availability id for the repeat
     */
    public Integer getAvailabilityID();

    /**
     * @return the unique id for the repeat
     */
    public Integer getId();
}
