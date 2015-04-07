package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDateDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.common.util.UID;

/**
 * an availability repeat that happens on a given day of the month, (i.e. the 28th)
 * @author drew
 */
public class AvailabilityRepeatMonthlyByDate extends AvailabilityRepeat {
    private int _dayOfMonth;
    
    /**
     * constructor for a new repeat used in loading
     * @param cache
     * @param id
     */
    private AvailabilityRepeatMonthlyByDate(Cache cache, int id) {
        super(cache, id);
    }
    
    /**
     * constructor for a new repeat used in creation
     * @param cache
     * @param avail
     * @param dayOfMonth
     */
    private AvailabilityRepeatMonthlyByDate(Cache cache, Availability avail, int dayOfMonth) {
        super(cache, avail);
        _dayOfMonth = dayOfMonth;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public AvailabilityRepeatMonthlyByDateModel getModel() {
        AvailabilityRepeatMonthlyByDateModel model = new AvailabilityRepeatMonthlyByDateModel();
        model.setId(getID());
        model.setAvailabilityID(getAvailability().getID());
        model.setDayOfMonth(_dayOfMonth);
        return model;
    }
    
    /**
     * initialize the fields of this skeleton repeat from a model
     * @param model the db model to initialize based on
     */
    public void initialize(AvailabilityRepeatMonthlyByDateModel model) {
        initialize();
        _dayOfMonth = model.getDayOfMonth();
    }
    
    /**
     * load a repeat into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param repeatD the id of the repeat to load
     * @return the repeat requested
     */
    protected static AvailabilityRepeatMonthlyByDate loadFromDB (Cache cache, int repeatID) {
        UID uid = new UID(TYPE_IDENTIFIER, repeatID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityRepeatMonthlyByDate.class); 
        else {
            AvailabilityRepeatMonthlyByDateModel model = cache.getDAOContext().dao(AvailabilityRepeatMonthlyByDateDAO.class).uniqueByID(repeatID).execute();
            if(model == null)
                return null;
            AvailabilityRepeatMonthlyByDate repeat = new AvailabilityRepeatMonthlyByDate(cache, repeatID);
            cache.cache(uid, repeat);
            repeat.initialize(model);
            return repeat;
        }
    }
    
    /**
     * create a new repeat with the specified parameters
     * @param businessID the id of the business to create the repeat for
     * @param dayOfMonth the day of the month to repeat on
     * @param avail the availability that the repeat pertains to
     * @return the new repeat
     */
    public static AvailabilityRepeatMonthlyByDate create (int businessID, Availability avail, int dayOfMonth) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityRepeatMonthlyByDate repeat = new AvailabilityRepeatMonthlyByDate(cache, avail, dayOfMonth);
        AvailabilityRepeatMonthlyByDateDAO dao = repeat.getDAO(AvailabilityRepeatMonthlyByDateDAO.class);
        repeat.setID(dao.getNextID());
        dao.add(repeat.getModel()).enqueue();
        cache.cache(new UID(repeat), repeat);
        return repeat;
    }
}
