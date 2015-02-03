package smartshift.accounts.hibernate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.accounts.hibernate.model.UserBusinessEmployeeModel;
import smartshift.accounts.hibernate.model.UserModel;
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
    public static List<BusinessModel> getBusinesses() {
        return GenericHibernateUtil.list(getAccountsSession(), BusinessModel.class);
    }
    
    /**
     * Get a business
     * @param id the business id to look up
     * @return The business - null if not found
     */
    public static BusinessModel getBusiness(Integer id) {
        return GenericHibernateUtil.unique(getAccountsSession(), BusinessModel.class, id);
    }
    
//    /**
//     * Get a map of business ID to business belonging to one user
//     * @param user The user to lookup
//     * @return the map
//     */
//    public static Map<Integer, BusinessModel> getUserBusinessMap(UserModel user) {
//        Map<Integer, BusinessModel> businesses = new HashMap<>();
//        for(UserBusinessEmployeeModel ube:user.getUserBusinessEmployees())
//            if(ube.getBusiness() != null && ube.getBusiness().getInactive() == false)
//                businesses.put(ube.getBusiness().getId(), ube.getBusiness());
//        return businesses;
//    }
//    
//    /**
//     * Get a business linked to a user
//     * @param user the user to lookup
//     * @param id the business id
//     * @return the business. null if not linked together
//     */
//    public static BusinessModel getUserBusiness(UserModel user, Integer id) {
//        for(UserBusinessEmployeeModel ube:user.getUserBusinessEmployees())
//            if(ube.getBusiness().getId().equals(id))
//                return ube.getBusiness();
//        return null;
//    }
//    
    
}
