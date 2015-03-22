package smartshift.business.cache.bo;

import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDateDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatMonthlyByDate extends AvailabilityRepeat {
    private int _dayOfMonth;
    
    private AvailabilityRepeatMonthlyByDateModel _model;
    
    private AvailabilityRepeatMonthlyByDate(Cache cache, int id) {
        super(cache, id);
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setDayOfMonth(_dayOfMonth);
                getDAO(AvailabilityRepeatMonthlyByDateDAO.class).update(_model);
            } else {
                _model = getDAO(AvailabilityRepeatMonthlyByDateDAO.class).add(_dayOfMonth).execute();
                setID(_model.getId());
            }
        } catch (HibernateException e) {
            getLogger().debug(e.getStackTrace());
        }
    }
    
    public void init(AvailabilityRepeatMonthlyByDateModel model) {
        _dayOfMonth = model.getDayOfMonth();
        _model = model;
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
}
