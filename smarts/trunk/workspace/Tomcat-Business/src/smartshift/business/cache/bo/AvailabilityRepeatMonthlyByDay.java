package smartshift.business.cache.bo;

import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDayDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatMonthlyByDay extends AvailabilityRepeat {
    private int _dayOfWeek;
    
    private AvailabilityRepeatMonthlyByDayModel _model;
    
    private AvailabilityRepeatMonthlyByDay(Cache cache, int id) {
        super(cache, id);
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setDayOfWeek(_dayOfWeek);
                getDAO(AvailabilityRepeatMonthlyByDayDAO.class).update(_model);
                super.save();
            } else {
                _model = getDAO(AvailabilityRepeatMonthlyByDayDAO.class).add(_dayOfWeek).execute();
                setID(_model.getId());
                super.save();
            }
        } catch (HibernateException e) {
            getLogger().debug(e.getStackTrace());
        }
    }
    
    public void init(AvailabilityRepeatMonthlyByDayModel model) {
        _dayOfWeek = model.getDayOfWeek();
        _model = model;
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
}
