package smartshift.accounts.cache.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.UserBusinessEmployeeDAO;
import smartshift.accounts.hibernate.dao.UserDAO;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROSet;
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
    private List<UserBusinessEmployeeModel> _busEmpModels;
    
    public User(String username, String email, String password) {
        _uname = username;
        _email = email;
        _passHash = password;
        _employeeIDs = new HashMap<Business, Integer>();
    }
    
    private User(UserModel model) {
        this(model.getUsername(), model.getEmail(), model.getPassHash());
        _model = model;
        loadAllChildren();
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
        if(!_employeeIDs.containsKey(bus))
            return -1;
        return _employeeIDs.get(bus);
    }
    
    public int getID() {
        if(_model != null) {
            return _model.getId();
        } 
        return -1;
    }
    
    public ROSet<Business> getBusinesses() {
        return new ROSet<Business>(_employeeIDs.keySet());
    }
    
    public void connect(Business bus, int empID) throws DBException {
        _employeeIDs.put(bus, empID);
        if(_busEmpModels == null)
            _busEmpModels = new ArrayList<UserBusinessEmployeeModel>();
        _busEmpModels.add(AccountsDAOContext.dao(UserBusinessEmployeeDAO.class).add(getID(), bus.getID(), empID));
        if(_busEmpModels.get(_busEmpModels.size()-1) == null)
            logger.error("constraint violation trying to connect user to employee: U" + getID()
                    + "@B"+bus.getID()+"--E"+empID);
        _busEmpModels.clear();
        _busEmpModels = null;
    }
    
    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setUsername(_uname);
                _model.setEmail(_email);
                _model.setPassHash(_passHash);
                AccountsDAOContext.dao(UserDAO.class).update(_model);      
            } else {
                _model = AccountsDAOContext.dao(UserDAO.class).add(_uname, _email, _passHash); 
            }
        } catch(DBException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        try {
            for(UserBusinessEmployeeModel ube : AccountsDAOContext.dao(UserBusinessEmployeeDAO.class).listByUser(_model.getId()))
                _employeeIDs.put(Business.load(ube.getBusinessID()), ube.getEmployeeID());
        } catch(Exception e) {
            logger.error("Failed to load children", e);
        }
    }
    
    public static User load(String username) {
        if(users == null)
            users = new HashMap<String, User>();
        if(!users.containsKey(username)) {
            UserModel model = AccountsDAOContext.dao(UserDAO.class).uniqueByUsername(username);
            if(model == null)
                return null;
            users.put(username, new User(model));
        }
        return users.get(username);
    }
    
    public static User createNewUser(String username, String email, String password) {
        User user = new User(username, email, password);
        users.put(username, user);
        user.save();
        return user;
    }
}
