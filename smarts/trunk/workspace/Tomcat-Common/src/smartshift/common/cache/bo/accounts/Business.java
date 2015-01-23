package smartshift.common.cache.bo.accounts;

import org.apache.log4j.Logger;
import smartshift.common.cache.bo.business.BusinessCache;
import smartshift.common.cache.bo.business.Employee;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.dao.accounts.BusinessDAO;
import smartshift.common.hibernate.model.accounts.BusinessModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.hibernate.Stored;

public class Business implements Stored {

    private String _name;
    
    private BusinessCache _cache;
    private BusinessModel _model;
    
    public Business(String name) {
        _name = name;
    }
    
    private Business(BusinessModel model) {
        this(model.getName());
        _model = model;
    }
    
    public BusinessCache getCache() {
        initCache();
        return _cache;
    }
    
    private void initCache() {
        if(_cache == null)
            _cache = new BusinessCache(this);
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
            Logger.getLogger(Business.class).debug(e.getStackTrace());
        }
        _cache.save();      
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
}
