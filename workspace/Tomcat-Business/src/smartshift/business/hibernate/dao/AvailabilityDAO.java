package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.common.hibernate.dao.tasks.criteria.ListByCriteriaTask;
import smartshift.common.hibernate.dao.tasks.model.AddTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * data access object for availability templates
 */
public class AvailabilityDAO extends BaseBusinessDAO<AvailabilityModel>{
    private static final SmartLogger logger = new SmartLogger(AvailabilityDAO.class);
    
    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityDAO(BusinessDAOContext context) {
        super(context, AvailabilityModel.class);
    }
    
    /** gets a task that gets all avails of a template
     * @param templateID the template id
     * @return the task object
     */
    public ListByCriteriaTask<AvailabilityModel> getByTemplate(Integer templateID) {
        return list(Restrictions.eq("templateID", templateID));
    }
    
    /** gets a task that saves a new avail, by params
     * @param templateID the template id
     * @param start the start time in minutes from 12am
     * @param duration the duration of the block in minutes
     * @param repeatEvery repeat every X
     * @param repeatCount repeat this many times
     * @param repeateOffset offset this by so many
     * @param unavailable if its an unavailable block or not
     * @return the task object
     */
    public AddTask<AvailabilityModel> add(Integer templateID, Integer start, Integer duration, Integer repeatEvery, Integer repeatCount, Integer repeateOffset, Boolean unavailable) {
        AvailabilityModel avail = new AvailabilityModel();
        avail.setId(getNextID());
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
