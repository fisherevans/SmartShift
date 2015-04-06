package smartshift.business.cache.bo;

import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDateDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatInterface;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatMonthlyByDate extends AvailabilityRepeat {
    private int _dayOfMonth;
    
    private AvailabilityRepeatMonthlyByDate(Cache cache, int id) {
        super(cache, id);
    }
    
    private AvailabilityRepeatMonthlyByDate(Cache cache, Availability avail, int dayOfMonth) {
        super(cache, avail);
        _dayOfMonth = dayOfMonth;
    }
    
    public AvailabilityRepeatMonthlyByDateModel getModel() {
        AvailabilityRepeatMonthlyByDateModel model = new AvailabilityRepeatMonthlyByDateModel();
        model.setId(getID());
        model.setAvailabilityID(getAvailability().getID());
        model.setDayOfMonth(_dayOfMonth);
        return model;
    }
    
    public void init(AvailabilityRepeatMonthlyByDateModel model) {
        _dayOfMonth = model.getDayOfMonth();
    }
    
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
            repeat.init(model);
            return repeat;
        }
    }
    
    public static AvailabilityRepeatMonthlyByDate create (int businessID, int dayOfMonth, Availability avail) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityRepeatMonthlyByDate repeat = new AvailabilityRepeatMonthlyByDate(cache, avail, dayOfMonth);
        AvailabilityRepeatMonthlyByDateDAO dao = repeat.getDAO(AvailabilityRepeatMonthlyByDateDAO.class);
        repeat.setID(dao.getNextID());
        dao.add(repeat.getModel()).enqueue();
        cache.cache(new UID(repeat), repeat);
        return repeat;
    }
}
