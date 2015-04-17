package smartshift.business.cache.bo;

import org.joda.time.DateTime;
import smartshift.business.hibernate.model.ShiftModel;

/**
 * a shift for an employee to work
 * @author drew
 */
public class Shift extends CachedObject{
    /** the type identifier for a shift */
    public static final String TYPE_IDENTIFIER = "S";
    
    private DateTime _start;
    private int _duration; // in minutes
    private Role _role;
    private Group _group;
    
    /**
     * constructor for a newly created shift
     * @param cache
     * @param start
     * @param duration
     * @param role
     */
    private Shift(Cache cache, DateTime start, int duration, Role role) {
        super(cache);
        _start = start;
        _duration = duration;
        _role = role;
    }
    
    /**
     * constructor for a newly loaded shift
     * @param cache
     * @param id
     */
    private Shift(Cache cache, int id) {
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
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    @Override
    public ShiftModel getModel() {
        ShiftModel model = new ShiftModel();
        model.setId(getID());
        model.setStart(_start.toDate());
        model.setDuration(_duration);
        return model;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    public static Shift load(Cache cache, int shiftID) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getGroupID() {
        return _group.getID();
    }
    
    public int getRoleID() {
        if(_role == null)
            return 0;
        return _role.getID();
    }
}
