package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityTemplateDAO extends BaseBusinessDAO<AvailabilityTemplateModel> {
    private final static SmartLogger logger = new SmartLogger(AvailabilityTemplateModel.class);
    
    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityTemplateDAO(BusinessDAOContext context) {
        super(context, AvailabilityTemplateModel.class);
    }
    
    /** gets all templates for one employee
     * @param employeeID the owner
     * @return all templates belonging to the given employee
     */
    public ROCollection<AvailabilityTemplateModel> listByEmployee(Integer employeeID) {
        ROCollection<AvailabilityTemplateModel> models = list(Restrictions.eq("employeeID", employeeID));
        return models;
    }
    
    /** adds a template with the given params
     * @param name the template name
     * @param employeeID the owner
     * @return the newly saved template
     * @throws DBException
     */
    public AvailabilityTemplateModel add(String name, Integer employeeID) throws DBException {
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
