package smartshift.business.cache.bo;

import smartshift.common.util.log4j.SmartLogger;

public abstract class AvailabilityRepeat extends CachedObject {
    public static final String TYPE_IDENTIFIER = "AR";
    
    private static final SmartLogger logger = new SmartLogger(AvailabilityRepeat.class);
    private Availability _availability;
    
    protected AvailabilityRepeat(Cache cache, int id) {
        super(cache, id);
    }

    protected AvailabilityRepeat(Cache cache, Availability avail) {
        super(cache);
        _availability = avail;
    }
    
    public Availability getAvailability() {
        return _availability;
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public void loadAllChildren() {
        // do nothing
    }
    
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
    
    protected SmartLogger getLogger() {
        return logger;
    }
}
