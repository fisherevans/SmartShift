package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.common.hibernate.dao.tasks.AddTask;
import smartshift.common.hibernate.dao.tasks.ListTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityTemplateDAO extends BaseBusinessDAO<AvailabilityTemplateModel>{
    private final static SmartLogger logger = new SmartLogger(AvailabilityTemplateModel.class);
    
    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityTemplateDAO(BusinessDAOContext context) {
        super(context, AvailabilityTemplateModel.class);
    }
    
    /** get a task that gets all templates for one employee
     * @param employeeID the owner
     * @return the task object
     */
    public ListTask<AvailabilityTemplateModel> listByEmployee(Integer employeeID) {
        return list(Restrictions.eq("employeeID", employeeID));
    }
    
    /** get a task that adds a template with the given params
     * @param name the template name
     * @param employeeID the owner
     * @return the task object
     */
    public AddTask<AvailabilityTemplateModel> add(String name, Integer employeeID) {
        AvailabilityTemplateModel template = new AvailabilityTemplateModel();
        template.setName(name);
        template.setEmployeeID(employeeID);
        return add(template);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
