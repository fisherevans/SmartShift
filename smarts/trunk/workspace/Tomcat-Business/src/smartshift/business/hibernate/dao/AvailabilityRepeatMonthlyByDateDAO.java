package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
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
    
    /**
     * get a task that Adds an AvailabilityRepeatMonthlyByDateModel
     * @param dayOfMonth the day of the month to repeat on
     * @return the task object
     */
    public AddTask<AvailabilityRepeatMonthlyByDateModel> add(Integer dayOfMonth) {
        AvailabilityRepeatMonthlyByDateModel model = new AvailabilityRepeatMonthlyByDateModel();
        model.setId(getNextID());
        model.setDayOfMonth(dayOfMonth);
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}