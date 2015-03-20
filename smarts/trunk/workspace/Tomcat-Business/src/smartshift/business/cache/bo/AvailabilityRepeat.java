package smartshift.business.cache.bo;

import smartshift.business.hibernate.model.AvailabilityRepeatInterface;
import smartshift.common.util.log4j.SmartLogger;

public abstract class AvailabilityRepeat extends CachedObject {
    public static final String TYPE_IDENTIFIER = "AR";
    
    private static final SmartLogger logger = new SmartLogger(Availability.class);
    
    private AvailabilityRepeatInterface _model;
    
    public AvailabilityRepeat(Cache cache) {
        super(cache);
    }
    
    private AvailabilityRepeat(Cache cache, AvailabilityRepeatInterface model) {
        this(cache);
        _model = model;
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public int getID() {
        if(_model != null)
            return _model.getAvailabilityID();
        return -1;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void loadAllChildren() {
        // do nothing
    }
    
    public static AvailabilityRepeat load(Cache cache, int availID) {
        // TODO Auto-generated method stub
        return null;
    }
}
