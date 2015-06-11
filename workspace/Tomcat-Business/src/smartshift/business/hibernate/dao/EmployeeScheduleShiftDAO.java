package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.EmployeeScheduleShiftModel;
import smartshift.common.hibernate.dao.tasks.criteria.CountByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.criteria.DeleteByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.criteria.UniqueByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the ShiftModel Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class EmployeeScheduleShiftDAO extends BaseBusinessDAO<EmployeeScheduleShiftModel> {
    /**
     * Logger for this DAO
     */
    private static SmartLogger logger = new SmartLogger(EmployeeScheduleShiftDAO.class);

    /**
     * @param context Base context for this business DAO
     */
    public EmployeeScheduleShiftDAO(BusinessDAOContext context) {
        super(context, EmployeeScheduleShiftModel.class);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
    
    /**
     * get a task that links a shift to an employee schedule
     * @param employeeScheduleID the employee id
     * @param shiftID the schedule id
     * @return the task
     */
    public AddTask<EmployeeScheduleShiftModel> link(Integer employeeScheduleID, Integer shiftID) {
        EmployeeScheduleShiftModel model = new EmployeeScheduleShiftModel();
        model.setEmployeeScheduleID(employeeScheduleID);
        model.setShiftID(shiftID);
        return add(model);
    }
    
    /** 
     * get a task that unlinks an employee from a schedule
     * @param employeeScheduleID the employee id
     * @param shiftID the schedule id
     * @return the task
     */
    public DeleteByCriteriaTask<EmployeeScheduleShiftModel> unlink(Integer employeeScheduleID, Integer shiftID) {
        return deleteByCriteria(getEmployeeScheduleShiftCriterion(employeeScheduleID, shiftID));
    }
    
    /** get a task that gets the model of an employee linked to a schedule
     * @param employeeScheduleID the employee schedule id
     * @param shiftID the shift id
     * @return the task object
     */
    public UniqueByCriteriaTask<EmployeeScheduleShiftModel> uniqueByEmployeeScheduleShift(Integer employeeScheduleID, Integer shiftID) {
        return uniqueByCriteria(getEmployeeScheduleShiftCriterion(employeeScheduleID, shiftID));
    }
    
    /**
     * get a task that counts links between a an employee and a schedule
     * @param employeeScheduleID the employee schedule id
     * @param shiftID the shift id
     * @return the task
     */
    public CountByCriteriaTask<EmployeeScheduleShiftModel> linkCount(Integer employeeScheduleID, Integer shiftID) {
        return rowCount(getEmployeeScheduleShiftCriterion(employeeScheduleID, shiftID));
    }
    
    private Criterion[] getEmployeeScheduleShiftCriterion(Integer employeeScheduleID, Integer shiftID) {
        return new Criterion[] { Restrictions.eq("employeeScheduleID", employeeScheduleID), Restrictions.eq("shiftID", shiftID) };
    }
}