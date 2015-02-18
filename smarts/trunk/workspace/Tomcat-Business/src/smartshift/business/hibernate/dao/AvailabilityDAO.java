package smartshift.business.hibernate.dao;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityDAO extends BaseBusinessDAO {

    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityDAO(DAOContext context) {
        super(context);
    }
    
    /** gets all avails
     * @return list of avails
     */
    public ROCollection<AvailabilityModel> getAvailabilities() {
        List<AvailabilityModel> avails = GenericHibernateUtil.list(getBusinessSession(), AvailabilityModel.class);
        return ROCollection.wrap(avails);
    }
    
    /** gets all avails of a template
     * @param templateID the template id
     * @return list of avails
     */
    public ROCollection<AvailabilityModel> getAvailabilitiesByTemplate(Integer templateID) {
        List<AvailabilityModel> templates = GenericHibernateUtil.list(getBusinessSession(), AvailabilityModel.class,
                Restrictions.eq("templateID", templateID));
        return ROCollection.wrap(templates);
    }
    
    /** gets a unique avail
     * @param id the unique id
     * @return the avail, null if not found
     */
    public AvailabilityModel getAvailability(Integer id) {
        AvailabilityModel avail = GenericHibernateUtil.unique(getBusinessSession(), AvailabilityModel.class, id);
        return avail;
    }
    
    /** saves a new avail, by params
     * @param templateID the template id
     * @param start the start time in minutes from 12am
     * @param duration the duration of the block in minutes
     * @param repeatEvery repeat every X
     * @param repeatCount repeat this many times
     * @param repeateOffset offset this by so many
     * @param unavailable if its an unavailable block or not
     * @return the saved model
     * @throws DBException
     */
    public AvailabilityModel addAvailability(Integer templateID, Integer start, Integer duration, Integer repeatEvery, Integer repeatCount, Integer repeateOffset, Boolean unavailable) throws DBException {
        AvailabilityModel avail = new AvailabilityModel();
        avail.setTemplateID(templateID);
        avail.setStart(start);
        avail.setDuration(duration);
        avail.setRepeatEvery(repeatEvery);
        avail.setRepeatCount(repeatCount);
        avail.setRepeateOffset(repeateOffset);
        avail.setUnavailable(unavailable);
        return addAvailability(avail);
    }
    
    /** save a new avail by a newly constructed model
     * @param newAvail the newly constructed avail model
     * @return the saved model
     * @throws DBException
     */
    public AvailabilityModel addAvailability(AvailabilityModel newAvail) throws DBException {
        newAvail = (AvailabilityModel) GenericHibernateUtil.save(getBusinessSession(), newAvail);
        return newAvail;
    }
    
    /** delete an avail by id
     * @param id the unique id
     * @throws DBException
     */
    public void deleteAvailabilityByID(Integer id) throws DBException {
        deleteAvailability(getAvailability(id));
    }
    
    /** delete an avail by model reference
     * @param avail the avail model to delete
     * @throws DBException
     */
    public void deleteAvailability(AvailabilityModel avail) throws DBException {
        GenericHibernateUtil.delete(getBusinessSession(), avail);
    }
}
