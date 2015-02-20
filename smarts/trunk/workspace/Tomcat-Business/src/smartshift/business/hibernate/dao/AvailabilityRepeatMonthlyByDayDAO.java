package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class AvailabilityRepeatMonthlyByDayDAO extends AvailabilityRepeatDAO<AvailabilityRepeatMonthlyByDayModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(AvailabilityRepeatMonthlyByDayDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public AvailabilityRepeatMonthlyByDayDAO(DAOContext context) {
        super(context, AvailabilityRepeatMonthlyByDayModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}