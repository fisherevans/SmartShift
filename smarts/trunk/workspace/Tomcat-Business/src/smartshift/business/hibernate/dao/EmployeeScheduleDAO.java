package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.EmployeeScheduleModel;
import smartshift.common.hibernate.dao.tasks.criteria.CountByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.criteria.DeleteByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.criteria.UniqueByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the EmployeeScheduleModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class EmployeeScheduleDAO extends BaseBusinessDAO<EmployeeScheduleModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(EmployeeScheduleDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public EmployeeScheduleDAO(BusinessDAOContext context) {
        super(context, EmployeeScheduleModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
    
    /**
     * get a task that links an employee to a schedule
     * @param employeeID the employee id
     * @param scheduleID the schedule id
     * @return the task
     */
    public AddTask<EmployeeScheduleModel> link(Integer employeeID, Integer scheduleID) {
        EmployeeScheduleModel model = new EmployeeScheduleModel();
        model.setEmployeeID(employeeID);
        model.setScheduleID(scheduleID);
        return add(model);
    }
    
    /** 
     * get a task that unlinks an employee from a schedule
     * @param employeeID the employee id
     * @param scheduleID the schedule id
     * @return the task
     */
    public DeleteByCriteriaTask<EmployeeScheduleModel> unlink(Integer employeeID, Integer scheduleID) {
        return deleteByCriteria(getEmployeeScheduleCriterion(employeeID, scheduleID));
    }
    
    /** get a task that gets the model of an employee linked to a schedule
     * @param employeeID the employee id
     * @param scheduleID the schedule id
     * @return the task object
     */
    public UniqueByCriteriaTask<EmployeeScheduleModel> uniqueByEmployeeSchedule(Integer employeeID, Integer scheduleID) {
        return uniqueByCriteria(getEmployeeScheduleCriterion(employeeID, scheduleID));
    }
    
    /**
     * get a task that counts links between a an employee and a schedule
     * @param scheduleID the schedule id
     * @param employeeID the employee id
     * @return the task
     */
    public CountByCriteriaTask<EmployeeScheduleModel> linkCount(Integer employeeID, Integer scheduleID) {
        return rowCount(getEmployeeScheduleCriterion(employeeID, scheduleID));
    }
    
    private Criterion[] getEmployeeScheduleCriterion(Integer employeeID, Integer scheduleID) {
        return new Criterion[] { Restrictions.eq("employeeID", employeeID), Restrictions.eq("scheduleID", scheduleID) };
    }
}