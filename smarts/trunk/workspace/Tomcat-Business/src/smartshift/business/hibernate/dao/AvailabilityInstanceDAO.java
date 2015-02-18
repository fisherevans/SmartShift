package smartshift.business.hibernate.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityInstanceDAO extends BaseBusinessDAO {

    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityInstanceDAO(DAOContext context) {
        super(context);
    }
    
    /** gets all instances
     * @return all instnaces
     */
    public ROCollection<AvailabilityInstanceModel> getAllInstances() {
        List<AvailabilityInstanceModel> instances = GenericHibernateUtil.list(getBusinessSession(), AvailabilityInstanceModel.class);
        return ROCollection.wrap(instances);
    }
    
    /** gets all instances owner by an owner
     * @param employeeID the owner
     * @return all instances owned by this owner
     */
    public ROCollection<AvailabilityInstanceModel> getInstancesByEmployee(Integer employeeID) {
        List<AvailabilityInstanceModel> instances = GenericHibernateUtil.list(getBusinessSession(), AvailabilityInstanceModel.class,
                Restrictions.eq("employeeID", employeeID));
        return ROCollection.wrap(instances);
    }
    
    /** gets all instances of a template
     * @param templateID the template id
     * @return all instances of a template
     */
    public ROCollection<AvailabilityInstanceModel> getInstancesByTemplate(Integer templateID) {
        List<AvailabilityInstanceModel> instances = GenericHibernateUtil.list(getBusinessSession(), AvailabilityInstanceModel.class,
                Restrictions.eq("templateID", templateID));
        return ROCollection.wrap(instances);
    }
    
    /** gets all instances owned by an owner of a template
     * @param employeeID the owner
     * @param templateID the template id
     * @return all instances of a template owned by this owner
     */
    public ROCollection<AvailabilityInstanceModel> getInstancesByEmployeeTemplate(Integer employeeID, Integer templateID) {
        List<AvailabilityInstanceModel> instances = GenericHibernateUtil.list(getBusinessSession(), AvailabilityInstanceModel.class,
                Restrictions.eq("employeeID", employeeID),
                Restrictions.eq("templateID", templateID));
        return ROCollection.wrap(instances);
    }
    
    /** a unique instance
     * @param id the id
     * @return the instance, null if not found
     */
    public AvailabilityInstanceModel getInstance(Integer id) {
        AvailabilityInstanceModel instance = GenericHibernateUtil.unique(getBusinessSession(), AvailabilityInstanceModel.class, id);
        return instance;
    }
    
    /** add an instance with the parameters given
     * @param templateID the template id
     * @param startDate when the instance starts
     * @param endDate when it ends
     * @param employeeID the owner
     * @return the saved model
     * @throws DBException
     */
    public AvailabilityInstanceModel addInstance(Integer templateID, Date startDate, Date endDate, Integer employeeID) throws DBException {
        AvailabilityInstanceModel instance = new AvailabilityInstanceModel();
        instance.setTemplateID(templateID);
        instance.setStartDate(startDate);
        instance.setEndDate(endDate);
        instance.setEmployeeID(employeeID);
        return addInstance(instance);
    }
    
    /** saves a newly constructed instance model
     * @param newInstance newly constructed model to save
     * @return the saved model
     * @throws DBException
     */
    public AvailabilityInstanceModel addInstance(AvailabilityInstanceModel newInstance) throws DBException {
        newInstance = (AvailabilityInstanceModel) GenericHibernateUtil.save(getBusinessSession(), newInstance);
        return newInstance;
    }
    
    /** delete an instance by id
     * @param id the instance id
     * @throws DBException
     */
    public void deleteInstanceyByID(Integer id) throws DBException {
        deleteInstance(getInstance(id));
    }
    
    /** delete and instance by model reference
     * @param instance the model
     * @throws DBException
     */
    public void deleteInstance(AvailabilityInstanceModel instance) throws DBException {
        GenericHibernateUtil.delete(getBusinessSession(), instance);
    }
}
