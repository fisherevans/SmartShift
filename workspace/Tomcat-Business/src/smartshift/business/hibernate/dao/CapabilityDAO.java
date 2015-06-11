package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.CapabilityModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class CapabilityDAO extends BaseBusinessDAO<CapabilityModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(CapabilityDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public CapabilityDAO(BusinessDAOContext context) {
        super(context, CapabilityModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}