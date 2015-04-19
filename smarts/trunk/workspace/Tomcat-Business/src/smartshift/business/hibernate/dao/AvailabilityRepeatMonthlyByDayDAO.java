package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
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
    public AvailabilityRepeatMonthlyByDayDAO(BusinessDAOContext context) {
        super(context, AvailabilityRepeatMonthlyByDayModel.class);
    }
    
    /**
     * get a task that Adds an AvailabilityRepeatMonthlyByDayModel
     * @param dayOfWeek the day of the week to repeat on (sunday = 0)
     * @return the task object
     */
    public AddTask<AvailabilityRepeatMonthlyByDayModel> add(Integer dayOfWeek) {
        AvailabilityRepeatMonthlyByDayModel model = new AvailabilityRepeatMonthlyByDayModel();
        model.setId(getNextID());
        model.setDayOfWeek(dayOfWeek);
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}