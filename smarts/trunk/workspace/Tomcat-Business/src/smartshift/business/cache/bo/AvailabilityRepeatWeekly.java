package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatWeeklyDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatWeekly extends AvailabilityRepeat {
    
    private int _dayOfWeek;
    
    private AvailabilityRepeatWeekly(Cache cache, int id) {
        super(cache, id);
    }
    
    private AvailabilityRepeatWeekly(Cache cache, Availability avail, int dayOfWeek) {
        super(cache, avail);
        _dayOfWeek = dayOfWeek;
    }
    
    public AvailabilityRepeatWeeklyModel getModel() {
        AvailabilityRepeatWeeklyModel model = new AvailabilityRepeatWeeklyModel();
        model.setAvailabilityID(getAvailability().getID());
        model.setDayOfWeek(_dayOfWeek);
        return model;
    }
    
    public void init(AvailabilityRepeatWeeklyModel model) {
        _dayOfWeek = model.getDayOfWeek();
    }
    
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
