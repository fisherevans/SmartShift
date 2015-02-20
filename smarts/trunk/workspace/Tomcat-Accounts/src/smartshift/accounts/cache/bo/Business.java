package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.BusinessDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class Business implements Stored {
    private static SmartLogger logger = new SmartLogger(Business.class);

    private static Map<Integer, Business> businesses;
    
    private int _id;
    private String _name;
    
    private BusinessModel _model;
    
    private Business(int id, String name) {
        _id = id;
        _name = name;
    }
    
    private Business(BusinessModel model) {
        this(model.getId(), model.getName());
        _model = model;
    }

    public int getID() {
        return _id;
    }
    
    public String getName() {
        return _name;
    }
    
    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setName(_name);
                AccountsDAOContext.dao(BusinessDAO.class).update(_model);
            } else {
                // we probably never want to create a new business here
                //_model = BusinessDAO.addBusiness(_name);
            }
        } catch (DBException e) {
            logger.debug(e.getStackTrace());
        }  
    }
    
    @Override
    public int hashCode() {
        return _id * 13 + _name.hashCode();
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
    
    public static Business load(int busID) {
        if(businesses == null)
            businesses = new HashMap<Integer, Business>();
        if(!businesses.containsKey(busID)) {
            BusinessModel model = AccountsDAOContext.dao(BusinessDAO.class).uniqueByID(busID);
            businesses.put(busID, new Business(model));           
        }
        return businesses.get(busID);
    }
}
