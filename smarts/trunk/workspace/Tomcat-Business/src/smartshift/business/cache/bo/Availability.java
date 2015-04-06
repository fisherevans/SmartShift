package smartshift.business.cache.bo;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.joda.time.LocalTime;
import smartshift.business.hibernate.dao.AvailabilityDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDateDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDayDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatWeeklyDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatYearlyDAO;
import smartshift.business.hibernate.dao.BaseBusinessDAO;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.business.hibernate.model.AvailabilityRepeatInterface;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class Availability extends CachedObject {
    public static final String TYPE_IDENTIFIER = "A";
    
    private static final SmartLogger logger = new SmartLogger(Availability.class);
    
    private AvailabilityTemplate _template;
    private List<AvailabilityRepeat> _repeats;
    private LocalTime _time;
    private int _duration;  //in minutes
    private int _repeatEvery;
    private int _repeatCount;
    private int _repeatOffset;
    private boolean _unavailable;
    
    private Availability(Cache cache, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        super(cache);
        _time = time;
        _duration = duration;
        _repeatEvery = repeatEvery;
        _repeatCount = repeatCount;
        _repeatOffset = repeatOffset;
        _unavailable = unavailable;
    }
    
    private Availability(Cache cache, int id) {
        super(cache, id);
    }
    
    private Availability(Cache cache, AvailabilityModel model) {
        this(cache, null , model.getDuration(), model.getRepeatEvery(), model.getRepeatCount(), model.getRepeateOffset(), model.getUnavailable());
        _time = LocalTime.fromMillisOfDay(model.getStart() * 1000);
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    public Availability addToTemplate(AvailabilityTemplate template) {
        if(_template != null) {
            return fork(template);
        }else{
            synchronized(this) {
                template.addedComponent(this);
                _template = template;
            }
            return null;
        }
    }

    private Availability fork(AvailabilityTemplate template) {
        Availability newAvail = create(getCache().getBusinessID(), _time, _duration, _repeatEvery, _repeatCount, _repeatOffset, _unavailable);
        synchronized(newAvail) {
            newAvail._template = template;
        }
        return newAvail;
    }
    
    public AvailabilityModel getModel() {
        AvailabilityModel model = new AvailabilityModel();
        model.setId(getID());
        model.setStart(_time.getHourOfDay());
        model.setDuration(_duration);
        model.setRepeatEvery(_repeatEvery);
        model.setRepeatCount(_repeatCount);
        model.setRepeateOffset(_repeatOffset);
        model.setUnavailable(_unavailable);
        if(_template != null)
            model.setTemplateID(_template.getID());
        return model;
    }

    @Override
    public void loadAllChildren() {
        _repeats = new ArrayList<AvailabilityRepeat>(3);
        for(AvailabilityRepeatMonthlyByDayModel model : getCache().getDAOContext().dao(AvailabilityRepeatMonthlyByDayDAO.class).listByAvailability(getID()).execute()) {
           if(model != null) {
               _repeats.add(AvailabilityRepeatMonthlyByDay.loadFromDB(getCache(), model.getId()));
           }
        }
        for(AvailabilityRepeatMonthlyByDateModel model : getCache().getDAOContext().dao(AvailabilityRepeatMonthlyByDateDAO.class).listByAvailability(getID()).execute()) {
           if(model != null) {
               _repeats.add(AvailabilityRepeatMonthlyByDate.loadFromDB(getCache(), model.getId()));
           }
        }
        for(AvailabilityRepeatWeeklyModel model : getCache().getDAOContext().dao(AvailabilityRepeatWeeklyDAO.class).listByAvailability(getID()).execute()) {
            if(model != null) {
                _repeats.add(AvailabilityRepeatWeekly.loadFromDB(getCache(), model.getId()));
            }
        }
        for(AvailabilityRepeatYearlyModel model : getCache().getDAOContext().dao(AvailabilityRepeatYearlyDAO.class).listByAvailability(getID()).execute()) {
            if(model != null) {
                _repeats.add(AvailabilityRepeatYearly.loadFromDB(getCache(), model.getId()));
            }
        }
            
    }

    public static Availability load(Cache cache, int availID) {
        UID uid = new UID(TYPE_IDENTIFIER, availID);
        if(cache.contains(uid))
            return cache.getCached(uid, Availability.class); 
        else {          
            Availability avail = new Availability(cache, availID);
            cache.cache(uid, avail);             
            avail.init();
            return avail;
        }
    }
    
    public void init() {
        AvailabilityModel model = getCache().getDAOContext().dao(AvailabilityDAO.class).uniqueByID(getID()).execute();
        _time = LocalTime.fromMillisOfDay(model.getStart() * 1000);
        _duration = model.getDuration();
        _repeatEvery = model.getRepeatEvery();
        _repeatCount = model.getRepeatCount();
        _repeatOffset = model.getRepeateOffset();
        _unavailable = model.getUnavailable();
    }
    
    public static Availability create(int businessID, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        Cache cache = Cache.getCache(businessID);
        Availability avail = new Availability(cache, time, duration, repeatEvery, repeatCount, repeatOffset, unavailable);
        AvailabilityDAO dao = avail.getDAO(AvailabilityDAO.class);
        avail.setID(dao.getNextID());
        dao.add(avail.getModel()).enqueue();
        cache.cache(new UID(avail), avail);
        return avail;
    }
}
