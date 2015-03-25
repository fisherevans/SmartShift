package smartshift.common.util.hibernate;

/**
 * @author drew
 * interface for a BO that is stored in the DB
 */
public interface Stored {
    /**
     * save this object to the DB
     */
    public void save();

    /**
     * save this object's children to the DB
     */
    public void saveRelationships();

    /**
     * load this object's children from the DB
     */
    public void loadAllChildren();
}
