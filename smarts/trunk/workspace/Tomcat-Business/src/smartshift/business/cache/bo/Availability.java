package smartshift.business.cache.bo;

import org.hibernate.HibernateException;
import org.joda.time.LocalTime;
import smartshift.business.hibernate.dao.AvailabilityDAO;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class Availability extends CachedObject {
    public static final String TYPE_IDENTIFIER = "A";
    
    private static final SmartLogger logger = new SmartLogger(Availability.class);
    
    private AvailabilityTemplate _template;
    private AvailabilityRepeat _repeat;
    private LocalTime _time;
    private int _duration;  //in minutes
    private int _repeatEvery;
    private int _repeatCount;
    private int _repeatOffset;
    private boolean _unavailable;
       
    private AvailabilityModel _model;
    
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
        _model = model;
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setStart(_time.getHourOfDay());
                _model.setDuration(_duration);
                _model.setRepeatEvery(_repeatEvery);
                _model.setRepeatCount(_repeatCount);
                _model.setRepeateOffset(_repeatOffset);
                _model.setUnavailable(_unavailable);
                getDAO(AvailabilityDAO.class).update(_model);
            } else {
                Integer templateID = null;
                if(_template != null)
                    templateID = _template.getID();
                    _model = getDAO(AvailabilityDAO.class).add(templateID, _time.getHourOfDay(), _duration, _repeatEvery, _repeatCount, _repeatOffset, _unavailable).execute();
                    setID(_model.getId());
            }
        } catch (HibernateException e) {
            logger.debug(e.getStackTrace());
        } 
    }
    
    public void addToTemplate(AvailabilityTemplate template) {
        if(_template != null) {
            fork(template);
        }else{
            template.add(this);
            _template = template;
        }
    }

    private void fork(AvailabilityTemplate template) {
        Availability old = new Availability(getCache(), _time, _duration, _repeatEvery, _repeatCount, _repeatOffset, _unavailable);
        old.save();
        getCache().cache(new UID(old), old);
        _template = template;
        save();
    }

    @Override
    public void loadAllChildren() {
        _repeat = AvailabilityRepeat.load(getCache(), getID());
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
        _model = model;
    }
    
    public static Availability create(int businessID, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        Cache cache = Cache.getCache(businessID);
        Availability avail = new Availability(cache, time, duration, repeatEvery, repeatCount, repeatOffset, unavailable);
        avail.save();
        cache.cache(new UID(avail), avail);
        return avail;
    }
}
