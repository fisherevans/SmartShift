package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.accounts.hibernate.dao.UserDAO;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

public class User implements Stored {
    private static final SmartLogger logger = new SmartLogger(User.class);
    
    private static Map<Integer, User> users;
    
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
    
    public static User load(int userID) {
        if(!users.containsKey(userID)) {
            UserModel model = UserDAO.getUserById(userID);
            users.put(userID, new User(model));
        }
        return users.get(userID);
    }
}
