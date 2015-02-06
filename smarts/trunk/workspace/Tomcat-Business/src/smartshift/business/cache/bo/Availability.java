package smartshift.business.cache.bo;

import org.joda.time.LocalTime;
import smartshift.common.util.log4j.SmartLogger;

public class Availability extends CachedObject implements Templatable {
    public static final String TYPE_IDENTIFIER = "A";
    
    private static final SmartLogger logger = new SmartLogger(Availability.class);
    
    private LocalTime _time;
    private int _duration;  //in minutes
    private int _repeatEvery;
    private int _repeatCount;
    private int _repeatOffset;
    private boolean _unavailable;
    
    public Availability(Cache cache) {
        super(cache);
        
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public int getID() {
        return -1;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub    
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
    }

}
