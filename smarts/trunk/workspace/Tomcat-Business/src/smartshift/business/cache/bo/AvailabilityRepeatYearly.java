package smartshift.business.cache.bo;

import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDayDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatYearlyDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatYearly extends AvailabilityRepeat{  
    private int _month;
    private int _dayOfMonth;
    
    private AvailabilityRepeatYearly(Cache cache, int id) {
        super(cache, id);
    }
    
    private AvailabilityRepeatYearly(Cache cache, Availability avail, int month, int dayOfMonth) {
        super(cache, avail);
        _month = month;
        _dayOfMonth = dayOfMonth;
    }
    
    public AvailabilityRepeatYearlyModel getModel() {
        AvailabilityRepeatYearlyModel model = new AvailabilityRepeatYearlyModel();
        model.setId(getID());
        model.setAvailabilityID(getAvailability().getID());
        model.setMonth(_month);
        model.setDayOfMonth(_dayOfMonth);
        return model;
    }
    
    public void init(AvailabilityRepeatYearlyModel model) {
        _month = model.getMonth();
        _dayOfMonth = model.getDayOfMonth();
    }
    
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
