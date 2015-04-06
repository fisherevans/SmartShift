package smartshift.business.cache.bo;

import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.model.ScheduleModel;

/**
 * an employee's weekly schedule
 * @author drew
 */
public class Schedule extends CachedObject {
    // maps from the day of the week to a set of shifts
    private Map<Integer, Set<Shift>> _shifts;
    
    /**
     * constructor for a newly loaded schedule
     * @param cache
     * @param id
     */
    private Schedule(Cache cache, int id) {
        super(cache, id);
    }

    /**
     * @see smartshift.common.util.Identifiable#typeCode()
     */
    @Override
    public String typeCode() {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        
    }
}
