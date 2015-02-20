package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AvailabilityRepeatMonthlyByDateDAO extends AvailabilityRepeatDAO<AvailabilityRepeatMonthlyByDateModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(AvailabilityRepeatMonthlyByDateDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public AvailabilityRepeatMonthlyByDateDAO(BusinessDAOContext context) {
        super(context, AvailabilityRepeatMonthlyByDateModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}