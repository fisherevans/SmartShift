package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDayDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.common.util.UID;

/**
 * an availability repeat on a specific day of the week (i.e. Wednesday) every month,
 *   the offset and frequency are determined on the availability itself, so this repeat
 *   might look like every 1st and 3rd Thursday of the month
 * @author drew
 */
public class AvailabilityRepeatMonthlyByDay extends AvailabilityRepeat {
    private int _dayOfWeek;
    
    /**
     * constructor for a new repeat used in loading
     * @param cache
     * @param id
     */
    private AvailabilityRepeatMonthlyByDay(Cache cache, int id) {
        super(cache, id);
    }
    
    /**
     * constructor for a new repeat used in creation
     * @param cache
     * @param avail
     * @param dayOfWeek
     */
    private AvailabilityRepeatMonthlyByDay(Cache cache, Availability avail, int dayOfWeek) {
        super(cache, avail);
        _dayOfWeek = dayOfWeek;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public AvailabilityRepeatMonthlyByDayModel getModel() {
        AvailabilityRepeatMonthlyByDayModel model = new AvailabilityRepeatMonthlyByDayModel();
        model.setId(getID());
        model.setAvailabilityID(getAvailability().getID());
        model.setDayOfWeek(_dayOfWeek);
        return model;
    }
    
    /**
     * initialize the fields of this skeleton repeat from a model
     * @param model the db model to initialize based on
     */
    public void initialize(AvailabilityRepeatMonthlyByDayModel model) {
        initialize((Employee) null);
        _dayOfWeek = model.getDayOfWeek();
    }
    
    /**
     * load a repeat into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param repeatD the id of the repeat to load
     * @return the repeat requested
     */
    protected static AvailabilityRepeatMonthlyByDay loadFromDB (Cache cache, int repeatID) {
        UID uid = new UID(TYPE_IDENTIFIER, repeatID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityRepeatMonthlyByDay.class); 
        else {
            AvailabilityRepeatMonthlyByDayModel model = cache.getDAOContext().dao(AvailabilityRepeatMonthlyByDayDAO.class).uniqueByID(repeatID).execute();
            if(model == null)
                return null;
            AvailabilityRepeatMonthlyByDay repeat = new AvailabilityRepeatMonthlyByDay(cache, repeatID);
            cache.cache(uid, repeat);
            repeat.initialize(model);
            return repeat;
        }
    }
    
    /**
     * create a new repeat with the specified parameters
     * @param businessID the id of the business to create the repeat for
     * @param dayOfWeek the day of the week to repeat on
     * @param avail the availability that the repeat pertains to
     * @return the new repeat
     */
    public static AvailabilityRepeatMonthlyByDay create (int businessID, Availability avail, int dayOfWeek) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityRepeatMonthlyByDay repeat = new AvailabilityRepeatMonthlyByDay(cache, avail, dayOfWeek);
        AvailabilityRepeatMonthlyByDayDAO dao = repeat.getDAO(AvailabilityRepeatMonthlyByDayDAO.class);
        repeat.setID(dao.getNextID());
        dao.add(repeat.getModel()).enqueue();
        cache.cache(new UID(repeat), repeat);
        return repeat;
    }
}
