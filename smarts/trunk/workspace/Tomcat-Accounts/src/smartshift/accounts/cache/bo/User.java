package smartshift.accounts.cache.bo;

import java.util.HashMap;
import java.util.Map;
import smartshift.accounts.hibernate.dao.AccountsDAOContext;
import smartshift.accounts.hibernate.dao.UserBusinessEmployeeDAO;
import smartshift.accounts.hibernate.dao.UserDAO;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.util.collections.ROSet;
import smartshift.common.util.hibernate.Stored;
import smartshift.common.util.log4j.SmartLogger;

/**
 * an application user, who can have 0, 1, or many associated employees
 * @author drew
 */
public class User implements Stored {
    private static final SmartLogger logger = new SmartLogger(User.class);
    
    /** the cache a users, mapped by username */
    private static Map<String, User> users;
    
    private int _id;
    private String _uname;
    private String _email;
    private String _passHash;
    private Map<Business, Integer> _employeeIDs;
    
    /**
     * constructor for a new user to be used in user creation
     * @param username
     * @param email
     * @param password
     */
    private User(String username, String email, String password) {
        _uname = username;
        _email = email;
        _passHash = password;
        _employeeIDs = new HashMap<Business, Integer>();
    }
    
    /**
     * constructor for a new user to be used when loading from the db
     * @param id
     */
    private User(int id) {       
        _id = id;
        UserModel model = AccountsDAOContext.dao(UserDAO.class).uniqueByID(_id).execute();
        _uname = model.getUsername();
        _email = model.getEmail();
        _passHash = model.getPassHash();
        _employeeIDs = new HashMap<Business, Integer>();
    }
    
    /**
     * @return the user's email
     */
    public String getEmail() {
        return _email;
    }
    
    /**
     * @return the user's password, hashed
     */
    public String getPassHash() {
        return _passHash;
    }
    
    /**
     * @return the user's username
     */
    public String getUserName() {
        return _uname;
    }
    
    /**
     * get the user's employee id within the context of a given business
     * @param bus the business to check in
     * @return the employee id within the specified business' context, or -1
     */
    public int getEmployeeID(Business bus) {
        if(!_employeeIDs.containsKey(bus))
            return -1;
        return _employeeIDs.get(bus);
    }
    
    /**
     * set the id
     * @param id the id to set
     */
    private synchronized void setID(int id) {
        _id = id;
    }
    
    /**
     * @return the user's id
     */
    public int getID() {
        return _id;
    }
    
    /**
     * @return the set of all business in which the user is connected to an employee
     */
    public ROSet<Business> getBusinesses() {
        return new ROSet<Business>(_employeeIDs.keySet());
    }
    
    /**
     * connect a user to an employee
     * @param bus the business to add the user to
     * @param empID the employee id to give the user in that context
     */
    public void connect(Business bus, int empID) {
        _employeeIDs.put(bus, empID);
        AccountsDAOContext.dao(UserBusinessEmployeeDAO.class).add(getID(), bus.getID(), empID).enqueue();
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public UserModel getModel() {
        UserModel model = new UserModel();
        model.setId(_id);
        model.setUsername(_uname);
        model.setEmail(_email);
        model.setPassHash(_passHash);
        return model;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        try {
            // populate the map from business to employeeID, loading all requisite businesses into memory
            for(UserBusinessEmployeeModel ube : AccountsDAOContext.dao(UserBusinessEmployeeDAO.class).listByUser(_id).execute())
                _employeeIDs.put(Business.load(ube.getBusinessID()), ube.getEmployeeID());
        } catch(Exception e) {
            logger.error("Failed to load children", e);
        }
    }
    
    /**
     * load a user into memory (pulls from the cache if it already exists in memory)
     * @param username the username of the user to load
     * @return the user requested
     */
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
    
    /**
     * create a new user with the specified parameters
     * @param username
     * @param email
     * @param password should be hashed
     * @return the newly created user
     */
    public static User create(String username, String email, String password) {
        User user = new User(username, email, password);
        UserDAO dao = AccountsDAOContext.dao(UserDAO.class);
        user.setID(dao.getNextID());
        dao.add(user.getModel()).enqueue();
        users.put(username, user);
        return user;
    }
}
