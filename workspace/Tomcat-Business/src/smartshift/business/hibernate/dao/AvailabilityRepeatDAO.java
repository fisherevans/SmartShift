package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.BusinessDAOContext;
import smartshift.business.hibernate.model.AvailabilityRepeatInterface;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.hibernate.dao.tasks.criteria.ListByCriteriaTask;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * DAO for availability repeats
 * @param <T> the model type 
 */
public abstract class AvailabilityRepeatDAO<T extends AvailabilityRepeatInterface> extends BaseBusinessDAO<T> {
    
    /**
     * Initializes the object.
     * @param context the dao context
     * @param modelClass the repeat model class obj
     */
    public AvailabilityRepeatDAO(BusinessDAOContext context, Class<T> modelClass) {
        super(context, modelClass);
    }

    /**
     * The list of valid availability repeat models
     */
    @SuppressWarnings("rawtypes")
    public final static Class[] REPEAT_TYPES = {
        AvailabilityRepeatWeeklyModel.class,
        AvailabilityRepeatMonthlyByDayModel.class,
        AvailabilityRepeatMonthlyByDateModel.class,
        AvailabilityRepeatYearlyModel.class
    };

    
    /** get a task that gets the repeats for a type for an avail
     * @param availabilityID the avail id
     * @return the task object
     */
    public ListByCriteriaTask<T> listByAvailability(Integer availabilityID) {
        return list(Restrictions.eq("availabilityID", availabilityID));
    }
}
