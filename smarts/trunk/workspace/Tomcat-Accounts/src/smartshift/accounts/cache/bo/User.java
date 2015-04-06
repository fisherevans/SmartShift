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
    
    private int _id;
    private String _uname;
    private String _email;
    private String _passHash;
    private Map<Business, Integer> _employeeIDs;
    
    private List<UserBusinessEmployeeModel> _busEmpModels;
    
    private User(String username, String email, String password) {
        _uname = username;
        _email = email;
        _passHash = password;
        _employeeIDs = new HashMap<Business, Integer>();
    }
    
    private User(int id) {       
        _id = id;
        UserModel model = AccountsDAOContext.dao(UserDAO.class).uniqueByID(_id).execute();
        _uname = model.getUsername();
        _email = model.getEmail();
        _passHash = model.getPassHash();
        _employeeIDs = new HashMap<Business, Integer>();
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
    
    private void setID(int id) {
        _id = id;
    }
    
    public int getID() {
        return _id;
    }
    
    public ROSet<Business> getBusinesses() {
        return new ROSet<Business>(_employeeIDs.keySet());
    }
    
    public void connect(Business bus, int empID) throws DBException {
        _employeeIDs.put(bus, empID);
        if(_busEmpModels == null)
            _busEmpModels = new ArrayList<UserBusinessEmployeeModel>();
        _busEmpModels.add(AccountsDAOContext.dao(UserBusinessEmployeeDAO.class).add(getID(), bus.getID(), empID).execute());
        if(_busEmpModels.get(_busEmpModels.size()-1) == null)
            logger.error("constraint violation trying to connect user to employee: U" + getID()
                    + "@B"+bus.getID()+"--E"+empID);
        _busEmpModels.clear();
        _busEmpModels = null;
    }
    
    public UserModel getModel() {
        UserModel model = new UserModel();
        model.setId(_id);
        model.setUsername(_uname);
        model.setEmail(_email);
        model.setPassHash(_passHash);
        return model;
    }

    @Override
    public void loadAllChildren() {
        try {
            for(UserBusinessEmployeeModel ube : AccountsDAOContext.dao(UserBusinessEmployeeDAO.class).listByUser(_id).execute())
                _employeeIDs.put(Business.load(ube.getBusinessID()), ube.getEmployeeID());
        } catch(Exception e) {
            logger.error("Failed to load children", e);
        }
    }
    
    public static User load(String username) {
        if(users == null)
            users = new HashMap<String, User>();
        if(!users.containsKey(username)) {
            UserModel model = AccountsDAOContext.dao(UserDAO.class).uniqueByUsername(username).execute();
            if(model == null)
                return null;
            users.put(username, new User(model.getId()));
        }
        return users.get(username);
    }
    
    public static User create(String username, String email, String password) {
        User user = new User(username, email, password);
        UserDAO dao = AccountsDAOContext.dao(UserDAO.class);
        user.setID(dao.getNextID());
        dao.add(user.getModel()).enqueue();
        users.put(username, user);
        return user;
    }
}
