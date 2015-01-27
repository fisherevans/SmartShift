package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.dao.accounts.UserDAO;
import smartshift.common.hibernate.dao.business.EmployeeDAO;
import smartshift.common.hibernate.model.accounts.UserModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class User implements Stored {
    private static final SmartLogger logger = new SmartLogger(User.class);
    
    private String _uname;
    private String _email;
    private String _passHash;
    private Map<Business, Integer> _employeeIDs;
    
    private UserModel _model;
    
    private User(String username, String email, String password) {
        _uname = username;
        _email = email;
        _passHash = password;
        _employeeIDs = new HashMap<Business, Integer>();
    }
    
    private User(UserModel model) {
        _uname = model.getUsername();
        _email = model.getEmail();
        _passHash = model.getPassHash();
        _model = model;
    }
    
    public String getEmail() {
        return _email;
    }
    
    public String getPassHash() {
        return _passHash;
    }
    
    public String getUserName() {
        return _uname;
    }
    
    public int getEmployeeID(Business bus) {
        return _employeeIDs.get(bus);
    }
    
    public void connect(Business bus, int empID) {
        _employeeIDs.put(bus, empID);
    }
    
    public void save() {
        try {
            if(_model == null) {
                _model = UserDAO.addUser(_uname, _email, _passHash);      
            } else {
                GenericHibernateUtil.save(UserDAO.getAccountsSession(), _model);
            }
        } catch(DBException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
}
