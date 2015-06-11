package smartshift.common.util.hibernate;

/**
 * @author drew
 * interface for a BO that is serialized in the DB
 */
public interface Stored {

    /**
     * load this object's children from the DB
     */
    public void loadAllChildren();
    
    /**
     * @return the hibernate model for this
     */
    public Object getModel();
}
