package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityDAO extends BaseBusinessDAO<AvailabilityModel> {
    private static final SmartLogger logger = new SmartLogger(AvailabilityDAO.class);
    
    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityDAO(DAOContext context) {
        super(context, AvailabilityModel.class);
    }
    
    /** gets all avails of a template
     * @param templateID the template id
     * @return list of avails
     */
    public ROCollection<AvailabilityModel> getByTemplate(Integer templateID) {
        ROCollection<AvailabilityModel> models = list(Restrictions.eq("templateID", templateID));
        return models;
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
    public AvailabilityModel add(Integer templateID, Integer start, Integer duration, Integer repeatEvery, Integer repeatCount, Integer repeateOffset, Boolean unavailable) throws DBException {
        AvailabilityModel avail = new AvailabilityModel();
        avail.setTemplateID(templateID);
        avail.setStart(start);
        avail.setDuration(duration);
        avail.setRepeatEvery(repeatEvery);
        avail.setRepeatCount(repeatCount);
        avail.setRepeateOffset(repeateOffset);
        avail.setUnavailable(unavailable);
        return add(avail);
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
