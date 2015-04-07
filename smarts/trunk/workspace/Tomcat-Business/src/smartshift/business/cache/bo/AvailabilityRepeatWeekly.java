package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatWeeklyDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.common.util.UID;

/**
 * an availability repeat that happens weekly on a specific day (i.e. Wednesday)
 * @author drew
 */
public class AvailabilityRepeatWeekly extends AvailabilityRepeat {
    
    private int _dayOfWeek;
    
    /**
     * constructor for a new repeat used in loading
     * @param cache
     * @param id
     */
    private AvailabilityRepeatWeekly(Cache cache, int id) {
        super(cache, id);
    }
    
    /**
     * constructor for a new repeat used in creation
     * @param cache
     * @param avail
     * @param dayOfWeek
     */
    private AvailabilityRepeatWeekly(Cache cache, Availability avail, int dayOfWeek) {
        super(cache, avail);
        _dayOfWeek = dayOfWeek;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public AvailabilityRepeatWeeklyModel getModel() {
        AvailabilityRepeatWeeklyModel model = new AvailabilityRepeatWeeklyModel();
        model.setAvailabilityID(getAvailability().getID());
        model.setDayOfWeek(_dayOfWeek);
        return model;
    }
    
    /**
     * initialize the fields of this skeleton repeat from a model
     * @param model the db model to initialize based on
     */
    public void init(AvailabilityRepeatWeeklyModel model) {
        init();
        _dayOfWeek = model.getDayOfWeek();
    }
    
    /**
     * load a repeat into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param repeatD the id of the repeat to load
     * @return the repeat requested
     */
    protected static AvailabilityRepeatWeekly loadFromDB (Cache cache, int repeatID) {
        UID uid = new UID(TYPE_IDENTIFIER, repeatID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityRepeatWeekly.class); 
        else {
            AvailabilityRepeatWeeklyModel model = cache.getDAOContext().dao(AvailabilityRepeatWeeklyDAO.class).uniqueByID(repeatID).execute();
            if(model == null)
                return null;
            AvailabilityRepeatWeekly repeat = new AvailabilityRepeatWeekly(cache, repeatID);
            cache.cache(uid, repeat);
            repeat.init(model);
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
    public static AvailabilityRepeatWeekly create (int businessID, Availability avail, int dayOfWeek) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityRepeatWeekly repeat = new AvailabilityRepeatWeekly(cache, avail, dayOfWeek);
        AvailabilityRepeatWeeklyDAO dao = repeat.getDAO(AvailabilityRepeatWeeklyDAO.class);
        repeat.setID(dao.getNextID());
        dao.add(repeat.getModel()).enqueue();
        cache.cache(new UID(repeat), repeat);
        return repeat;
    }
}
