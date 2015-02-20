package smartshift.business.hibernate.dao;

import java.util.Date;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityInstanceDAO extends BaseBusinessDAO<AvailabilityInstanceModel> {
    private static final SmartLogger logger = new SmartLogger(AvailabilityInstanceDAO.class);
    
    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityInstanceDAO(DAOContext context) {
        super(context, AvailabilityInstanceModel.class);
    }
    
    /** gets all instances owner by an owner
     * @param employeeID the owner
     * @return all instances owned by this owner
     */
    public ROCollection<AvailabilityInstanceModel> listByEmployee(Integer employeeID) {
        ROCollection<AvailabilityInstanceModel> models = list(Restrictions.eq("employeeID", employeeID));
        return models;
    }
    
    /** gets all instances of a template
     * @param templateID the template id
     * @return all instances of a template
     */
    public ROCollection<AvailabilityInstanceModel> listByTemplate(Integer templateID) {
        ROCollection<AvailabilityInstanceModel> models = list(Restrictions.eq("templateID", templateID));
        return models;
    }
    
    /** gets all instances owned by an owner of a template
     * @param employeeID the owner
     * @param templateID the template id
     * @return all instances of a template owned by this owner
     */
    public ROCollection<AvailabilityInstanceModel> listByEmployeeTemplate(Integer employeeID, Integer templateID) {
        ROCollection<AvailabilityInstanceModel> models = list(Restrictions.eq("employeeID", employeeID), Restrictions.eq("templateID", templateID));
        return models;
    }
    
    /** add an instance with the parameters given
     * @param templateID the template id
     * @param startDate when the instance starts
     * @param endDate when it ends
     * @param employeeID the owner
     * @return the saved model
     * @throws DBException
     */
    public AvailabilityInstanceModel add(Integer templateID, Date startDate, Date endDate, Integer employeeID) throws DBException {
        AvailabilityInstanceModel instance = new AvailabilityInstanceModel();
        instance.setTemplateID(templateID);
        instance.setStartDate(startDate);
        instance.setEndDate(endDate);
        instance.setEmployeeID(employeeID);
        return add(instance);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
