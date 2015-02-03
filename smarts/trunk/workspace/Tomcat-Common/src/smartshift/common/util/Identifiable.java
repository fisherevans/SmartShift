package smartshift.common.util;

/**
 * @author drew
 * An interface giving the ability to construct UIDs for objects
 */
public interface Identifiable{
    
    /**
     * @return a string representing the type of the object
     */
    public String typeCode();
    
    /**
     * @return an id representing the object instance
     */
    public int getID();
    
    /**
     * @return a UID for the object
     */
    public UID getUID();
}
