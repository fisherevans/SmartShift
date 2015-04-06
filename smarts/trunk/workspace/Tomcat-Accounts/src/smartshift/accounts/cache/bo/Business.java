package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.BusinessDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class Business implements Stored {
    private static SmartLogger logger = new SmartLogger(Business.class);

    private static Map<Integer, Business> businesses;
    
    private final int _id;
    private final String _name;
    
    private Business(int id, String name) {
        _id = id;
        _name = name;
    }
    
    private Business(int id) {
        _id = id;
        BusinessModel model = AccountsDAOContext.dao(BusinessDAO.class).uniqueByID(_id).execute();
        _name = model.getName();
    }

    public int getID() {
        return _id;
    }
    
    public String getName() {
        return _name;
    }
    
    @Override
    public BusinessModel getModel() {
        BusinessModel model = new BusinessModel();
        model.setId(_id);
        model.setName(_name);
        return model;
    }
    
    @Override
    public int hashCode() {
        return _id * 13 + _name.hashCode();
    }

    @Override
    public void loadAllChildren() {
        // TODO Drew, should we initialize the cache here? The business is created when the account rmi service tells this app to connect.
    }
    
    public static Business load(int busID) {
        if(businesses == null)
            businesses = new HashMap<Integer, Business>();
        if(!businesses.containsKey(busID)) {
            BusinessModel model = AccountsDAOContext.dao(BusinessDAO.class).uniqueByID(busID).execute();
            businesses.put(busID, new Business(busID));           
        }
        return businesses.get(busID);
    }
}
