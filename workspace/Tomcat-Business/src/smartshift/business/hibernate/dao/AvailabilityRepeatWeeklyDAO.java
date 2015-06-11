package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
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
    
    /**
     * get a task that Adds an AvailabilityRepeatWeeklyModel
     * @param dayOfWeek the day of the week to repeat on (sunday = 0)
     * @return the task object
     */
    public AddTask<AvailabilityRepeatWeeklyModel> add(Integer dayOfWeek) {
        AvailabilityRepeatWeeklyModel model = new AvailabilityRepeatWeeklyModel();
        model.setId(getNextID());
        model.setDayOfWeek(dayOfWeek);
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}