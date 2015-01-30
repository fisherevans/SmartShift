package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.accounts.hibernate.dao.UserDAO;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.accounts.rmi.BusinessServiceManager;
import smartshift.common.hibernate.DBException;
import smartshift.common.rmi.interfaces.BusinessServiceInterface;
import smartshift.common.util.collections.ROSet;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class User implements Stored {
    private static final SmartLogger logger = new SmartLogger(User.class);
    
    private static Map<String, User> users;
    
    private String _uname;
    private String _email;
    private String _passHash;
    private Map<Business, Integer> _employeeIDs;
    
    private UserModel _model;
    
    public User(String username, String email, String password) {
        _uname = username;
        _email = email;
        _passHash = password;
        _employeeIDs = new HashMap<Business, Integer>();
    }
    
    private User(UserModel model) {
        this(model.getUsername(), model.getEmail(), model.getPassHash());
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
    
    public int getID() {
        if(_model != null) {
            return _model.getId();
        } 
        return -1;
    }
    
    public void connect(Business bus, int empID) {
        _employeeIDs.put(bus, empID);
        ROSet<BusinessServiceInterface> bs = BusinessServiceManager.getBusinessServices(bus.getID());
        for(BusinessServiceInterface bsi : bs) {
            try {
               bsi.linkEmployeeToUser(bus.getID(), _model.getId(), empID);
            } catch (Exception e) {
                logger.debug(e);
            }
        }
        
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
    
    public static User load(String username) {
        if(!users.containsKey(username)) {
            UserModel model = UserDAO.getUserByUsername(username);
            if(model == null)
                return null;
            users.put(username, new User(model));
        }
        return users.get(username);
    }
}
