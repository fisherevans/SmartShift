package smartshift.business.cache.bo;

import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.model.ScheduleModel;

/**
 * an employee's weekly schedule
 * @author drew
 */
public class ScheduleTemplate extends CachedObject {
    /** the type identifier for a schedule */
    public static final String TYPE_IDENTIFIER = "ST";
    // maps from the day of the week to a set of shifts
    private Map<Integer, Set<Shift>> _shifts;
    private String _name;
    
    /**
     * constructor for a newly loaded schedule
     * @param cache
     * @param id
     */
    private ScheduleTemplate(Cache cache, int id) {
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
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    public ScheduleModel getModel() {
        // TODO implement
        return null;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        // do nothing
    }

    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }
}
