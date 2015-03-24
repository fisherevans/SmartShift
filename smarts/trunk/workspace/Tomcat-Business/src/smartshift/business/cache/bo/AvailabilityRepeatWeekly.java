package smartshift.business.cache.bo;

import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityRepeatWeeklyDAO;
import smartshift.business.hibernate.dao.EmployeeDAO;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.EmployeeModel;
import smartshift.common.util.UID;

public class AvailabilityRepeatWeekly extends AvailabilityRepeat {
    
    private int _dayOfWeek;
    
    private AvailabilityRepeatWeeklyModel _model;
    
    private AvailabilityRepeatWeekly(Cache cache, int id) {
        super(cache, id);
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setDayOfWeek(_dayOfWeek);
                getDAO(AvailabilityRepeatWeeklyDAO.class).update(_model);
                super.save();
            } else {
                _model = getDAO(AvailabilityRepeatWeeklyDAO.class).add(_dayOfWeek).execute();
                setID(_model.getId());
                super.save();
            }
        } catch (HibernateException e) {
            getLogger().debug(e.getStackTrace());
        }
    }
    
    public void init(AvailabilityRepeatWeeklyModel model) {
        _dayOfWeek = model.getDayOfWeek();
        _model = model;
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
}
