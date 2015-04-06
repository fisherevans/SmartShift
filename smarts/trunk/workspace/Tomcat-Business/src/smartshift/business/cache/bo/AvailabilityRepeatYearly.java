package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatYearlyDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.UID;

/**
 * an availability repeat that occurs on a given month on a given day every year
 *   (i.e. March 16)
 * @author drew
 */
public class AvailabilityRepeatYearly extends AvailabilityRepeat{  
    private int _month;
    private int _dayOfMonth;
    
    /**
     * constructor for a new repeat used in loading
     * @param cache
     * @param id
     */
    private AvailabilityRepeatYearly(Cache cache, int id) {
        super(cache, id);
    }
    
    /**
     * constructor for a new repeat used in creation
     * @param cache
     * @param avail
     * @param dayOfMonth
     */
    private AvailabilityRepeatYearly(Cache cache, Availability avail, int month, int dayOfMonth) {
        super(cache, avail);
        _month = month;
        _dayOfMonth = dayOfMonth;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public AvailabilityRepeatYearlyModel getModel() {
        AvailabilityRepeatYearlyModel model = new AvailabilityRepeatYearlyModel();
        model.setId(getID());
        model.setAvailabilityID(getAvailability().getID());
        model.setMonth(_month);
        model.setDayOfMonth(_dayOfMonth);
        return model;
    }
    
    /**
     * initialize the fields of this skeleton repeat from a model
     * @param model the db model to initialize based on
     */
    public void init(AvailabilityRepeatYearlyModel model) {
        _month = model.getMonth();
        _dayOfMonth = model.getDayOfMonth();
    }
    
    /**
     * load a repeat into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param repeatD the id of the repeat to load
     * @return the repeat requested
     */
    protected static AvailabilityRepeatYearly loadFromDB (Cache cache, int repeatID) {
        UID uid = new UID(TYPE_IDENTIFIER, repeatID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityRepeatYearly.class); 
        else {
            AvailabilityRepeatYearlyModel model = cache.getDAOContext().dao(AvailabilityRepeatYearlyDAO.class).uniqueByID(repeatID).execute();
            if(model == null)
                return null;
            AvailabilityRepeatYearly repeat = new AvailabilityRepeatYearly(cache, repeatID);
            cache.cache(uid, repeat);
            repeat.init(model);
            return repeat;
        }
    }
    
    /**
     * create a new repeat with the specified parameters
     * @param businessID the id of the business to create the repeat for
     * @param month the month to repeat on
     * @param dayOfMonth the day of that month to repeat on
     * @param avail the availability that the repeat pertains to
     * @return the new repeat
     */
    public static AvailabilityRepeatYearly create (int businessID, Availability avail, int month, int dayOfMonth) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityRepeatYearly repeat = new AvailabilityRepeatYearly(cache, avail, month, dayOfMonth);
        AvailabilityRepeatYearlyDAO dao = repeat.getDAO(AvailabilityRepeatYearlyDAO.class);
        repeat.setID(dao.getNextID());
        dao.add(repeat.getModel()).enqueue();
        cache.cache(new UID(repeat), repeat);
        return repeat;
    }
}
