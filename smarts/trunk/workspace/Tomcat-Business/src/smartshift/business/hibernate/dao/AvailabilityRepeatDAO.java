package smartshift.business.hibernate.dao;

import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityRepeatInterface;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.collections.ROCollection;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * DAO for availability repeats
 * @param <T> the repeat model class
 */
public abstract class AvailabilityRepeatDAO<T extends AvailabilityRepeatInterface> extends BaseBusinessDAO<T> {
    
    /**
     * Initializes the object.
     * @param context the dao context
     * @param modelClass the repeat model class obj
     */
    public AvailabilityRepeatDAO(DAOContext context, Class<T> modelClass) {
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

    
    /** gets the repeats for a type for an avail
     * @param availabilityID the avail id
     * @return the list of avail repeats of this type for this avail id
     */
    public ROCollection<T> listByAvailability(Integer availabilityID) {
        ROCollection<T> models = list(Restrictions.eq("availabilityID", availabilityID));
        return models;
    }
}
