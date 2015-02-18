package smartshift.business.hibernate.dao;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityTemplateDAO extends BaseBusinessDAO {

    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityTemplateDAO(DAOContext context) {
        super(context);
    }
    
    /** gets all templates
     * @return all templates
     */
    public ROCollection<AvailabilityTemplateModel> getAllTemplates() {
        List<AvailabilityTemplateModel> templates = GenericHibernateUtil.list(getBusinessSession(), AvailabilityTemplateModel.class);
        return ROCollection.wrap(templates);
    }
    
    /** gets all templates for one employee
     * @param employeeID the owner
     * @return all templates belonging to the given employee
     */
    public ROCollection<AvailabilityTemplateModel> getTemplatesByEmployee(Integer employeeID) {
        List<AvailabilityTemplateModel> templates = GenericHibernateUtil.list(getBusinessSession(), AvailabilityTemplateModel.class,
                Restrictions.eq("employeeID", employeeID));
        return ROCollection.wrap(templates);
    }
    
    /** get a specific template by id
     * @param id the template id
     * @return the unique template, null if not found
     */
    public AvailabilityTemplateModel getTemplate(Integer id) {
        AvailabilityTemplateModel template = GenericHibernateUtil.unique(getBusinessSession(), AvailabilityTemplateModel.class, id);
        return template;
    }
    
    /** adds a template with the given params
     * @param name the template name
     * @param employeeID the owner
     * @return the newly saved template
     * @throws DBException
     */
    public AvailabilityTemplateModel addTemplate(String name, Integer employeeID) throws DBException {
        AvailabilityTemplateModel template = new AvailabilityTemplateModel();
        template.setName(name);
        template.setEmployeeID(employeeID);
        return addTemplate(template);
    }
    
    /** saved a given model (must be newly constructed by hand)
     * @param newTemplate the template to save
     * @return the saved template
     * @throws DBException
     */
    public AvailabilityTemplateModel addTemplate(AvailabilityTemplateModel newTemplate) throws DBException {
        newTemplate = (AvailabilityTemplateModel) GenericHibernateUtil.save(getBusinessSession(), newTemplate);
        return newTemplate;
    }
    
    /** deletes a template by id
     * @param id the template id to delete
     * @throws DBException
     */
    public void deleteTemplateByID(Integer id) throws DBException {
        deleteTemplate(getTemplate(id));
    }
    
    /** deletes a template by model reference
     * @param template the template to delete
     * @throws DBException
     */
    public void deleteTemplate(AvailabilityTemplateModel template) throws DBException {
        GenericHibernateUtil.delete(getBusinessSession(), template);
    }
}
