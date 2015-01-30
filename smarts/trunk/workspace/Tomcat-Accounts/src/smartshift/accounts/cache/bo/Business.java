package smartshift.accounts.cache.bo;

import java.util.Map;
import smartshift.accounts.hibernate.dao.BusinessDAO;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.GenericHibernateUtil;
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
            if(_model != null)
                GenericHibernateUtil.save(BusinessDAO.getAccountsSession(), _model);
            else {
                //_model = BusinessDAO.addBusiness(_name);
            }
        } catch (DBException e) {
            logger.debug(e.getStackTrace());
        }  
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
    
    public static Business load(int busID) {
        if(!businesses.containsKey(busID)) {
            BusinessModel model = BusinessDAO.getBusiness(busID);
            businesses.put(busID, new Business(model));           
        }
        return businesses.get(busID);
    }
}
