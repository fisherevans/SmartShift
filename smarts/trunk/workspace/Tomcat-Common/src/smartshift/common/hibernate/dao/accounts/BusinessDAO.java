package smartshift.common.hibernate.dao.accounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.common.hibernate.model.accounts.Business;
import smartshift.common.hibernate.model.accounts.User;
import smartshift.common.hibernate.model.accounts.UserBusinessEmployee;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * The data access object for the Business Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class BusinessDAO extends BaseAccountsDAO {

    /**
     * Fetch all businesses
     * @return The list of all businesses
     */
    public static List<Business> getBusinesses() {
        return GenericHibernateUtil.list(getAccountsSession(), Business.class);
    }
    
    /**
     * Get a business
     * @param id the business id to look up
     * @return The business - null if not found
     */
    public static Business getBusiness(Integer id) {
        return GenericHibernateUtil.unique(getAccountsSession(), Business.class, id);
    }
    
    /**
     * Get a map of business ID to business belonging to one user
     * @param user The user to lookup
     * @return the map
     */
    public static Map<Integer, Business> getUserBusinessMap(User user) {
        Map<Integer, Business> businesses = new HashMap<>();
        for(UserBusinessEmployee ube:user.getUserBusinessEmployees())
            if(ube.getBusiness() != null && ube.getBusiness().getInactive() == false)
                businesses.put(ube.getBusiness().getId(), ube.getBusiness());
        return businesses;
    }
    
    /**
     * Get a business linked to a user
     * @param user the user to lookup
     * @param id the business id
     * @return the business. null if not linked together
     */
    public static Business getUserBusiness(User user, Integer id) {
        for(UserBusinessEmployee ube:user.getUserBusinessEmployees())
            if(ube.getBusiness().getId().equals(id))
                return ube.getBusiness();
        return null;
    }
    
    
}
