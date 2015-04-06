package smartshift.business.cache.bo;

import java.util.Map;
import java.util.Set;
import smartshift.business.hibernate.model.ScheduleModel;

public class Schedule extends CachedObject {
    // maps from the day of the week to a set of shifts
    private Map<Integer, Set<Shift>> _shifts;
    
    private Schedule(Cache cache, int id) {
        super(cache, id);
    }

    @Override
    public String typeCode() {
        // TODO Auto-generated method stub
        return null;
    }

    public ScheduleModel getModel() {
        // TODO implement
        return null;
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub
        
    }
}
