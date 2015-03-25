package smartshift.business.hibernate.dao;

import java.util.Date;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.ListTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityInstanceDAO extends BaseBusinessDAO<AvailabilityInstanceModel>{
    private static final SmartLogger logger = new SmartLogger(AvailabilityInstanceDAO.class);
    
    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityInstanceDAO(BusinessDAOContext context) {
        super(context, AvailabilityInstanceModel.class);
    }
    
    /** get a task that gets all instances owner by an owner
     * @param employeeID the owner
     * @return the task object
     */
    public ListTask<AvailabilityInstanceModel> listByEmployee(Integer employeeID) {
        return list(Restrictions.eq("employeeID", employeeID));
    }
    
    /** gets a task that gets all instances of a template
     * @param templateID the template id
     * @return the task object
     */
    public ListTask<AvailabilityInstanceModel> listByTemplate(Integer templateID) {
        return list(Restrictions.eq("templateID", templateID));
    }
    
    /** get a task that gets all instances owned by an owner of a template
     * @param employeeID the owner
     * @param templateID the template id
     * @return the task object
     */
    public ListTask<AvailabilityInstanceModel> listByEmployeeTemplate(Integer employeeID, Integer templateID) {
        return list(Restrictions.eq("templateID", templateID), Restrictions.eq("employeeID", employeeID));
    }
    
    /** get a task that adds an instance with the parameters given
     * @param templateID the template id
     * @param startDate when the instance starts
     * @param endDate when it ends
     * @return the task object
     */
    public AddTask<AvailabilityInstanceModel> add(Integer templateID, Date startDate, Date endDate) {
        AvailabilityInstanceModel instance = new AvailabilityInstanceModel();
        instance.setTemplateID(templateID);
        instance.setStartDate(startDate);
        instance.setEndDate(endDate);
        return add(instance);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
