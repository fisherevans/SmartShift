package smartshift.business.cache.bo;

import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityRepeatYearlyDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatYearly extends AvailabilityRepeat{  
    private int _month;
    private int _dayOfMonth;
    
    private AvailabilityRepeatYearlyModel _model;
    
    private AvailabilityRepeatYearly(Cache cache, int id) {
        super(cache, id);
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setMonth(_month);
                _model.setDayOfMonth(_dayOfMonth);
                getDAO(AvailabilityRepeatYearlyDAO.class).update(_model);
            } else {
                _model = getDAO(AvailabilityRepeatYearlyDAO.class).add(_month, _dayOfMonth).execute();
                setID(_model.getId());
            }
        } catch (HibernateException e) {
            getLogger().debug(e.getStackTrace());
        }
    }
    
    public void init(AvailabilityRepeatYearlyModel model) {
        _month = model.getMonth();
        _dayOfMonth = model.getDayOfMonth();
        _model = model;
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
}
