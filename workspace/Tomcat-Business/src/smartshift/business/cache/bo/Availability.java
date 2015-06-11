package smartshift.business.cache.bo;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalTime;
import smartshift.business.hibernate.dao.AvailabilityDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDateDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatMonthlyByDayDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatWeeklyDAO;
import smartshift.business.hibernate.dao.AvailabilityRepeatYearlyDAO;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDateModel;
import smartshift.business.hibernate.model.AvailabilityRepeatMonthlyByDayModel;
import smartshift.business.hibernate.model.AvailabilityRepeatWeeklyModel;
import smartshift.business.hibernate.model.AvailabilityRepeatYearlyModel;
import smartshift.common.util.UID;

/**
 * an availability, indicating a set of dates and times when an employee is or is not available
 *   based on a set of repeats
 * @author drew
 *
 */
public class Availability extends CachedObject {
    /** the type identifier for an availability object */
    public static final String TYPE_IDENTIFIER = "A";
        
    private AvailabilityTemplate _template;
    private List<AvailabilityRepeat> _repeats;
    private LocalTime _time;
    private int _duration;  //in minutes
    private int _repeatEvery;
    private int _repeatCount;
    private int _repeatOffset;
    private boolean _unavailable;
    
    /**
     * constructor for a new availability to be used in creation
     * @param cache
     * @param time
     * @param duration
     * @param repeatEvery
     * @param repeatCount
     * @param repeatOffset
     * @param unavailable
     */
    private Availability(Cache cache, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        super(cache);
        _time = time;
        _duration = duration;
        _repeatEvery = repeatEvery;
        _repeatCount = repeatCount;
        _repeatOffset = repeatOffset;
        _unavailable = unavailable;
    }
    
    /**
     * constructor for a new availability to be used in loading from the db
     * @param cache
     * @param id
     */
    private Availability(Cache cache, int id) {
        super(cache, id);
    }

    /**
     * @see smartshift.common.util.Identifiable#typeCode()
     */
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    /**
     * add this availability to the given template, if this is already on a template,
     *   this will be forked to be added to the new one
     * @param template the template to add it to
     * @return null unless a new availability had to be forked, otherwise the new
     *   availability
     */
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

    /**
     * fork this availability, creating a new one that is an identical copy for
     *   the new template
     * @param template the template to fork for
     * @return the newly created availability
     */
    private Availability fork(AvailabilityTemplate template) {
        Availability newAvail = create(getCache().getBusinessID(), _time, _duration, _repeatEvery, _repeatCount, _repeatOffset, _unavailable);
        synchronized(newAvail) {
            newAvail._template = template;
        }
        return newAvail;
    }
    
    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
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
    
    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
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

    /**
     * load an availability into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param availID the id of the availability to load
     * @return the availability requested
     */
    public static Availability load(Cache cache, int availID) {
        UID uid = new UID(TYPE_IDENTIFIER, availID);
        if(cache.contains(uid))
            return cache.getCached(uid, Availability.class); 
        else {          
            Availability avail = new Availability(cache, availID);
            cache.cache(uid, avail);             
            avail.initialize(null);
            return avail;
        }
    }
    
    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        AvailabilityModel model = getCache().getDAOContext().dao(AvailabilityDAO.class).uniqueByID(getID()).execute();
        _time = LocalTime.fromMillisOfDay(model.getStart() * 1000);
        _duration = model.getDuration();
        _repeatEvery = model.getRepeatEvery();
        _repeatCount = model.getRepeatCount();
        _repeatOffset = model.getRepeateOffset();
        _unavailable = model.getUnavailable();
    }
    
    /**
     * create a new availability with the requested parameters
     * @param businessID the businessID for the cache to create the object in
     * @param time the start time
     * @param duration the duration of availability
     * @param repeatEvery the frequency (i.e. every 2nd week)
     * @param repeatCount the number of times to repeat (i.e. for 5 repeat cycles)
     * @param repeatOffset the number of cycles before the repeat should start
     * @param unavailable does this availability represent time available or time unavailable?
     * @return the new availability
     */
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
