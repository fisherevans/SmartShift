package smartshift.business.cache.bo;

import org.joda.time.LocalTime;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.common.util.log4j.SmartLogger;

public abstract class Availability extends CachedObject {
    public static final String TYPE_IDENTIFIER = "A";
    
    private static final SmartLogger logger = new SmartLogger(Availability.class);
    
    private LocalTime _time;
    private int _duration;  //in minutes
    private int _repeatEvery;
    private int _repeatCount;
    private int _repeatOffset;
    private boolean _unavailable;
       
    private AvailabilityModel _model;
    
    public Availability(Cache cache, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        super(cache);
        _time = time;
        _duration = duration;
        _repeatEvery = repeatEvery;
        _repeatCount = repeatCount;
        _repeatOffset = repeatOffset;
        _unavailable = unavailable;
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
    public int getID() {
        if(_model != null)
            return _model.getId();
        return -1;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub    
    }
    
    public void addToTemplate(AvailabilityTemplate template) {
        template.add(this);
    }

    @Override
    public void loadAllChildren() {
        // do nothing
    }

    public static Availability load(Cache cache, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static Availability create(Cache cache, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        // TODO
        return null;
    }
}
