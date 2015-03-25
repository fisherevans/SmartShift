package smartshift.business.hibernate.dao;

import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
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
    
    /**
     * get a task that Adds an AvailabilityRepeatYearlyModel
     * @param month the month to repeat on
     * @param dayOfMonth the day of the month to repeat on
     * @return the task object
     */
    public AddTask<AvailabilityRepeatYearlyModel> add(Integer month, Integer dayOfMonth) {
        AvailabilityRepeatYearlyModel model = new AvailabilityRepeatYearlyModel();
        model.setMonth(month);
        model.setDayOfMonth(dayOfMonth);
        return add(model);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}