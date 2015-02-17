package smartshift.accounts.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the User Business Employee Relationship
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class UserBusinessEmployeeDAO extends BaseAccountsDAO {
    private static final SmartLogger logger = new SmartLogger(UserBusinessEmployeeDAO.class);

    /**
     * Gets the user business employee relation for a user
     * @param userID the user to lookup
     * @param businessID the businessid
     * @param employeeID the employee id
     * @return the ube object. null if not found
     */
    public static UserBusinessEmployeeModel getUBE(Integer userID, Integer businessID, Integer employeeID) {
        UserBusinessEmployeeModel ube = null;
        try {
            ube = GenericHibernateUtil.uniqueByCriterea(getAccountsSession(), UserBusinessEmployeeModel.class,
                    Restrictions.eq("userID", userID),
                    Restrictions.eq("businessID", businessID),
                    Restrictions.eq("employeeID", employeeID));
        } catch(Exception e) {
            logger.debug("failed to fetch ube", e);
        }
        return ube; // else
    }
    
    /**
     * Gets the user business employee relations for a user and business
     * @param userID the user to lookup
     * @param businessID the businessid
     * @return the ube objects. null if not found
     */
    public static List<UserBusinessEmployeeModel> getUBEs(Integer userID, Integer businessID) {
        List<UserBusinessEmployeeModel> ubeList = new ArrayList<>();
        try {
            ubeList.addAll((GenericHibernateUtil.list(getAccountsSession(), UserBusinessEmployeeModel.class,
                    Restrictions.eq("userID", userID),
                    Restrictions.eq("businessID", businessID))));
        } catch(Exception e) {
            logger.debug("failed to fetch ube by user and business", e);
        }
        return ubeList; // else
    }
    
    /**
     * Gets the user business employee relations for a user
     * @param userID the user to lookup
     * @return the ube objects. null if not found
     */
    public static List<UserBusinessEmployeeModel> getUBEs(Integer userID) {
        List<UserBusinessEmployeeModel> ubeList = new ArrayList<>();
        try {
            ubeList.addAll((GenericHibernateUtil.list(getAccountsSession(), UserBusinessEmployeeModel.class,
                    Restrictions.eq("userID", userID))));
        } catch(Exception e) {
            logger.debug("failed to fetch ubes by user", e);
        }
        return ubeList; // else
    }
    
    /** Adds a ube relationship
     * @param userID the user id
     * @param businessID the business id
     * @param employeeID the employee id
     * @return the ube object, null on error
     */
    public static UserBusinessEmployeeModel addUBE(Integer userID, Integer businessID, Integer employeeID) {
        try {
            UserBusinessEmployeeModel ube = new UserBusinessEmployeeModel();
            ube.setUserID(userID);
            ube.setBusinessID(businessID);
            ube.setEmployeeID(employeeID);
            GenericHibernateUtil.save(getAccountsSession(), ube);
            return ube;
        } catch(Exception e) {
            logger.debug("failed to fetch ube", e);
        }
        return null;
    }
    
    /** Deletes the user business relatiosnhip
     * @param ube
     * @return true if it was deleted
     */
    public static boolean deleteUBE(UserBusinessEmployeeModel ube) {
        try {
            GenericHibernateUtil.delete(getAccountsSession(), ube);
            return true;
        } catch(Exception e) {
            logger.debug("failed to fetch ube", e);
        }
        return false;
    }

    /** gets a UBE link
     * @param ubeID the PK
     * @return null if not found
     */
    public static UserBusinessEmployeeModel getUBEByID(Integer ubeID) {
        UserBusinessEmployeeModel ube = null;
        try {
            ube = GenericHibernateUtil.unique(getAccountsSession(), UserBusinessEmployeeModel.class, ubeID);
        } catch(Exception e) {
            logger.debug("failed to fetch ube", e);
        }
        return ube; // else
    }
}
