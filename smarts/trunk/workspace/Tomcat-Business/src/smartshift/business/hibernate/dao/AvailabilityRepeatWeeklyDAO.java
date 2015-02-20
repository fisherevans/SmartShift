package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AvailabilityRepeatWeeklyDAO extends AvailabilityRepeatDAO<AvailabilityRepeatWeeklyModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(AvailabilityRepeatWeeklyDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public AvailabilityRepeatWeeklyDAO(BusinessDAOContext context) {
        super(context, AvailabilityRepeatWeeklyModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}