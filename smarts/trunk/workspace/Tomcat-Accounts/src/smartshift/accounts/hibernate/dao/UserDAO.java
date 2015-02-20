package smartshift.accounts.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import smartshift.accounts.hibernate.model.UserModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the UserModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserDAO extends BaseAccountsDAO<UserModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(UserDAO.class);

    /**
     * Initializes the object.
     */
    public UserDAO() {
        super(UserModel.class);
    }
    
    /**
     * Fetch a UserModel by Username
     * @param username the username to lookup
     * @return the UserModel - null if not found
     */
    public UserModel uniqueByUsername(String username) {
        UserModel model = uniqueByCriteria(Restrictions.eq("username", username));
        return model;
    }
    
    /**
     * Fetch a UserModel by email
     * @param email the email to lookup
     * @return the UserModel - null if not found
     */
    public UserModel uniqueByEmail(String email) {
        UserModel model = uniqueByCriteria(Restrictions.eq("email", email));
        return model;
    }
    
    /**
     * Add a UserModel
     * @param username 
     * @param email 
     * @param password 
     * @return the created UserModel
     * @throws DBException if there was an error creating the UserModel
     */
    public UserModel add(String username, String email, String password) throws DBException {
        UserModel model = new UserModel();
        model.setUsername(username);
        model.setEmail(email);
        model.setPassHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        model = add(model);
        return model;
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}