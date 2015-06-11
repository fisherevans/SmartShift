package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.BusinessDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.common.util.hibernate.Stored;

/**
 * a business
 * @author drew
 *
 */
public class Business implements Stored {
    /** cache of businesses, keyed by integer id */
    private static Map<Integer, Business> businesses;
    
    private final int _id;
    private final String _name;
    
    /**
     * constructs a new business--to be used when creating a new business
     * @param id
     * @param name
     */
    private Business(int id, String name) {
        _id = id;
        _name = name;
    }
    
    /**
     * constructs a new business by looking it up from the db
     * @param id
     */
    private Business(int id) {
        _id = id;
        BusinessModel model = AccountsDAOContext.dao(BusinessDAO.class).uniqueByID(_id).execute();
        _name = model.getName();
    }

    /**
     * @return the business id
     */
    public int getID() {
        return _id;
    }
    
    /**
     * @return the business name
     */
    public String getName() {
        return _name;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    @Override
    public BusinessModel getModel() {
        BusinessModel model = new BusinessModel();
        model.setId(_id);
        model.setName(_name);
        return model;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return _id * 13 + _name.hashCode();
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        // TODO Drew, should we initialize the cache here? The business is created when the account rmi service tells this app to connect.
    }
    
    /**
     * load a business into memory (pulls from the cache if it already exists in memory)
     * @param busID the id of the business to load
     * @return the business requested
     */
    public static Business load(int busID) {
        if(businesses == null)
            businesses = new HashMap<Integer, Business>();
        if(!businesses.containsKey(busID))
            businesses.put(busID, new Business(busID));           
        return businesses.get(busID);
    }
}
