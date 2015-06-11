package smartshift.accounts.hibernate.dao;

import smartshift.accounts.hibernate.model.BusinessModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the Business Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class BusinessDAO extends BaseAccountsDAO<BusinessModel> {
    private static final SmartLogger logger = new SmartLogger(BusinessModel.class);
    
    /**
     * Initializes the object.
     */
    public BusinessDAO() {
        super(BusinessModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
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
