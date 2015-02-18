package smartshift.business.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import smartshift.business.hibernate.model.AvailabilityRepeatInterface;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.hibernate.DBException;
import smartshift.common.util.collections.ROCollection;
import smartshift.common.util.hibernate.GenericHibernateUtil;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 * DAO for availability repeats
 */
public class AvailabilityRepeatDAO extends BaseBusinessDAO {
    
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

    /**
     * Initializes the object.
     * @param context
     */
    public AvailabilityRepeatDAO(DAOContext context) {
        super(context);
    }
    
    /** gets the repeats for a type for an avail
     * @param <T>
     * @param type the repeat type class
     * @param availabilityID the avail id
     * @return the list of avail repeats of this type for this avail id
     */
    public <T extends AvailabilityRepeatInterface> ROCollection<T> getRepeatsByAvailability(Class<T> type, Integer availabilityID) {
        List<T> repeats = GenericHibernateUtil.list(getBusinessSession(), type,
                Restrictions.eq("availabilityID", availabilityID));
        return ROCollection.wrap(repeats);
    }
    
    /** gets all repeats of all types for an avail
     * @param availabilityID the avail id
     * @return list of ALL repeats, of all types, for this avail id
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ROCollection<AvailabilityRepeatInterface> getAllRepeatsByAvailability(Integer availabilityID) {
        List<AvailabilityRepeatInterface> repeats = new ArrayList<AvailabilityRepeatInterface>();
        for(Class type:REPEAT_TYPES)
            repeats.addAll(GenericHibernateUtil.list(getBusinessSession(),
                    type, Restrictions.eq("availabilityID", availabilityID)));
        return ROCollection.wrap(repeats);
    }
    
    /** gets a repeat of a type by id
     * @param <T>
     * @param type the avail repeat type
     * @param repeatId the repeat id (unique for this type)
     * @return the repeat model, null if not founf
     */
    public <T extends AvailabilityRepeatInterface> T getRepeatById(Class<T> type, Integer repeatId) {
        T repeat = GenericHibernateUtil.unique(getBusinessSession(), type,
                Restrictions.eq("id", repeatId));
        return repeat;
    }
    
    /** adds a repeat of a type
     * @param <T>
     * @param type the repeat type
     * @param repeat the actual model to save
     * @return the saved model
     * @throws DBException
     */
    @SuppressWarnings("unchecked")
    public <T extends AvailabilityRepeatInterface> T addRepeat(Class<T> type, AvailabilityRepeatInterface repeat) throws DBException {
        if(!type.isInstance(repeat))
            throw new RuntimeException(repeat.getClass().getSimpleName() + " is not a compatible type with " + type.getSimpleName());
        T newRepeat = (T) GenericHibernateUtil.save(getBusinessSession(), repeat);
        return newRepeat;
    }
    
    /** deletes a repeat of a type by id
     * @param <T>
     * @param type the repeat type
     * @param repeatId the repeat id (unique for this type)
     * @throws DBException
     */
    public <T extends AvailabilityRepeatInterface> void deleteRepeatByID(Class<T> type, Integer repeatId) throws DBException {
        deleteRepeat(getRepeatById(type, repeatId));
    }
    
    /** deletes a repeat by model refence
     * @param repeat the repeat model
     * @throws DBException
     */
    public void deleteRepeat(AvailabilityRepeatInterface repeat) throws DBException {
        GenericHibernateUtil.delete(getBusinessSession(), repeat);
    }
}
