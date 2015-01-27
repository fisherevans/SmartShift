package smartshift.accounts.cache.bo;

import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.dao.accounts.BusinessDAO;
import smartshift.common.hibernate.model.accounts.BusinessModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class Business implements Stored {
    private static SmartLogger logger = new SmartLogger(Business.class);

    private String _name;
    
    private BusinessModel _model;
    
    public Business(String name) {
        _name = name;
    }
    
    private Business(BusinessModel model) {
        this(model.getName());
        _model = model;
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
}
