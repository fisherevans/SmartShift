package smartshift.business.cache.bo;

import smartshift.common.util.log4j.SmartLogger;

/**
 * an abstract class to wrap the different types of availability repeat (monthly by date, monthly by day, weekly, yearly)
 * @author drew
 *
 */
public abstract class AvailabilityRepeat extends CachedObject {
    /** the type identifier for an availability repeat */
    public static final String TYPE_IDENTIFIER = "AR";
    
    private static final SmartLogger logger = new SmartLogger(AvailabilityRepeat.class);
    
    private Availability _availability;
    
    /**
     * constructor for a new availability repeat loaded from the db
     * @param cache
     * @param id
     */
    protected AvailabilityRepeat(Cache cache, int id) {
        super(cache, id);
    }

    /**
     * constructor for a new availability repeat used in creation
     * @param cache
     * @param avail the availability that this repeat pertains to
     */
    protected AvailabilityRepeat(Cache cache, Availability avail) {
        super(cache);
        _availability = avail;
    }
    
    /**
     * @return the availability that this repeat pertains to
     */
    public Availability getAvailability() {
        return _availability;
    }

    /**
     * @see smartshift.common.util.Identifiable#typeCode()
     */
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        // do nothing
    }
    
    /**
     * load a repeat into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param repeatID the id of the repeat to load
     * @return the repeat requested
     */
    public static AvailabilityRepeat load(Cache cache, int repeatID) {
        AvailabilityRepeat repeat = null;
        repeat = AvailabilityRepeatMonthlyByDate.loadFromDB(cache, repeatID);
        if(repeat != null)
            return repeat;
        repeat = AvailabilityRepeatMonthlyByDay.loadFromDB(cache, repeatID);
        if(repeat != null)
            return repeat;
        repeat = AvailabilityRepeatWeekly.loadFromDB(cache, repeatID);
        if(repeat != null)
            return repeat;
        repeat = AvailabilityRepeatYearly.loadFromDB(cache, repeatID);
        return repeat;
    }
    
    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        // do nothing
    }
    
    /**
     * @return the logger for availability repeats
     */
    protected SmartLogger getLogger() {
        return logger;
    }
}
