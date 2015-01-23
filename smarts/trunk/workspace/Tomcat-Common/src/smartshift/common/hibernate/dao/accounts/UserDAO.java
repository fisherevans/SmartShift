package smartshift.common.hibernate.dao.accounts;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import smartshift.common.hibernate.DBException;
import smartshift.common.hibernate.model.accounts.UserModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * The data access object for the UserModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserDAO extends BaseAccountsDAO {
    /**
     * Logger for this DAO
     */
    private static Logger logger = Logger.getLogger(UserDAO.class);
    
    /**
     * Fetch a UserModel by UserModelname
     * @param UserModelname the UserModelname to lookup
     * @return the UserModel - null if not found
     */
    public static UserModel getUserByUsername(String UserModelname) {
        logger.debug("UserModelDAO.getUserModelByUserModelname() Enter - " + UserModelname);
        UserModel UserModel = GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), UserModel.class, Restrictions.eq("username", UserModelname));
        logger.debug("UserModelDAO.getUserModelByUserModelname() Got " + (UserModel == null ? "null" : UserModel.getUsername()));
        return UserModel;
    }
    
    /**
     * Fetch a UserModel by email
     * @param email the email to lookup
     * @return the UserModel - null if not found
     */
    public static UserModel getUserByEmail(String email) {
        logger.debug("UserModelDAO.getUserModelByEmail() Enter - " + email);
        UserModel UserModel = GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), UserModel.class, Restrictions.eq("email", email));
        logger.debug("UserModelDAO.getUserModelByEmail() Got " + (UserModel == null ? "null" : UserModel.getUsername()));
        return UserModel;
    }
    
    /**
     * Fetch a UserModel by id
     * @param id the id to lookup
     * @return the UserModel - null if not found
     */
    public static UserModel getUserById(Integer id) {
        logger.debug("UserModelDAO.getUserModelById() Enter - " + id);
        UserModel UserModel = GenericHibernateUtil.unique(getAccountsSession(), UserModel.class, id);
        logger.debug("UserModelDAO.getUserModelById() Got " + (UserModel == null ? "null" : UserModel.getUsername()));
        return UserModel;
    }
    
    /**
     * Get all UserModels
     * @return the list of UserModels
     */
    public static List<UserModel> getUsers() {
        logger.debug("UserModelDAO.getUserModels() Enter");
        List<UserModel> UserModels = GenericHibernateUtil.list(getAccountsSession(), UserModel.class);
        logger.debug("UserModelDAO.getUserModels() Got UserModel count: " + UserModels.size());
        return UserModels;
    }
    
    /**
     * Add a UserModel
     * @param username 
     * @param email 
     * @param password 
     * @return the created UserModel
     * @throws DBException if there was an error creating the UserModel
     */
    public static UserModel addUser(String username, String email, String password) throws DBException {
        logger.debug("UserModelDAO.addUserModel() Enter");
        UserModel UserModel = new UserModel();
        UserModel.setUsername(username);
        UserModel.setEmail(email);
        UserModel.setPassHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        GenericHibernateUtil.save(getAccountsSession(), UserModel);
        logger.debug("UserModelDAO.addUserModel() Success");
        return UserModel;
    }
}