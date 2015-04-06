package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDayDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatMonthlyByDay extends AvailabilityRepeat {
    private int _dayOfWeek;
    
    private AvailabilityRepeatMonthlyByDay(Cache cache, int id) {
        super(cache, id);
    }
    
    private AvailabilityRepeatMonthlyByDay(Cache cache, Availability avail, int dayOfWeek) {
        super(cache, avail);
        _dayOfWeek = dayOfWeek;
    }

    public AvailabilityRepeatMonthlyByDayModel getModel() {
        AvailabilityRepeatMonthlyByDayModel model = new AvailabilityRepeatMonthlyByDayModel();
        model.setId(getID());
        model.setAvailabilityID(getAvailability().getID());
        model.setDayOfWeek(_dayOfWeek);
        return model;
    }
    
    public void init(AvailabilityRepeatMonthlyByDayModel model) {
        _dayOfWeek = model.getDayOfWeek();
    }
    
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
            repeat.init(model);
            return repeat;
        }
    }
    
    public static AvailabilityRepeatMonthlyByDay create (int businessID, int dayOfWeek) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityRepeatMonthlyByDay repeat = new AvailabilityRepeatMonthlyByDay(cache, dayOfWeek);
        AvailabilityRepeatMonthlyByDayDAO dao = repeat.getDAO(AvailabilityRepeatMonthlyByDayDAO.class);
        repeat.setID(dao.getNextID());
        dao.add(repeat.getModel()).enqueue();
        cache.cache(new UID(repeat), repeat);
        return repeat;
    }
}
