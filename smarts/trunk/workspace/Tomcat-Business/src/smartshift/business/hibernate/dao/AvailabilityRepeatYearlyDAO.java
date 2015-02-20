package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AvailabilityRepeatYearlyDAO extends AvailabilityRepeatDAO<AvailabilityRepeatYearlyModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(AvailabilityRepeatYearlyDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public AvailabilityRepeatYearlyDAO(BusinessDAOContext context) {
        super(context, AvailabilityRepeatYearlyModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}