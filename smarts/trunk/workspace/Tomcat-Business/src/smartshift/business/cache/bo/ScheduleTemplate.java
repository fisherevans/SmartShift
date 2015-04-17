package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * an employee's weekly schedule
 * @author drew
 */
public class ScheduleTemplate extends CachedObject {
    /** the type identifier for a schedule template*/
    public static final String TYPE_IDENTIFIER = "ST";
    // tracks the head version number
    private int _head;
    // map from version number to the version
    private Map<Integer, ScheduleTemplateVersion> _versions;
    private Group _group;
    
    
    private ScheduleTemplate(Cache cache, Group group) {
        super(cache);
        _group = group;
        _head = 0;
        _versions = new HashMap<Integer, ScheduleTemplateVersion>();
    }
    
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
    
    public void versionAdded(ScheduleTemplateVersion version) {
        _versions.put(++_head, version);
    }

    /**
     * always returns null
     * @return null
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    @Override
    public Object getModel() {
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
    
    public static ScheduleTemplate create() {
        return null;
    }
    
    public static ScheduleTemplate load() {
        return null;
    }
}
