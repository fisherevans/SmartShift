package smartshift.business.cache.bo;

import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import smartshift.business.hibernate.model.ScheduleTemplateVersionModel;
import smartshift.common.util.collections.ROCollection;

public class ScheduleTemplateVersion extends CachedObject {
    /** the type identifier for a schedule template version */
    public static final String TYPE_IDENTIFIER = "STV";
    // maps from day of the week to a list of shifts
    private Map<Integer, List<Shift>> _shifts;
    private String _name;
    private DateTime _createTS;
    
    private ScheduleTemplateVersion(Cache cache) {
        super(cache);
        // TODO Auto-generated constructor stub
    }
    
    private ScheduleTemplateVersion(Cache cache, int id) {
        super(cache, id);
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public void loadAllChildren() {
        // TODO Auto-generated method stub

    }

    /**
     * for schedule template versions, the model isn't an entirely complete
     *   representation of the data stored
     * @return the partial model
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    @Override
    public ScheduleTemplateVersionModel getModel() {
        ScheduleTemplateVersionModel model = new ScheduleTemplateVersionModel();
        model.setId(getID());
        model.setName(_name);
        model.setCreateTimestamp(_createTS.toDate());
        return model;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @param cache
     * @param tempID
     * @return
     */
    public static ScheduleTemplateVersion load(Cache cache, int tempID) {
        // TODO Auto-generated method stub
        return null;
    }

    public ROCollection<Shift> getShifts() {
        // TODO Auto-generated method stub
        return null;
    }
}
