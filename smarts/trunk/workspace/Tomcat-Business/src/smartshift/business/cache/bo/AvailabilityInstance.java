package smartshift.business.cache.bo;

import org.joda.time.LocalDate;
import smartshift.business.hibernate.dao.AvailabilityInstanceDAO;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.common.util.UID;

/**
 * an instance of an availability template, binding days and times to an actual date
 *   range
 * @author drew
 *
 */
public class AvailabilityInstance extends CachedObject {
    /** the type identifier for an availability instance */
    public static final String TYPE_IDENTIFIER = "AI";
    
    private AvailabilityTemplate _template;
    private LocalDate _startDate;
    private LocalDate _endDate;
    
    /**
     * constructor for a new instance to be used in creation
     * @param cache
     * @param start
     * @param end
     */
    private AvailabilityInstance(Cache cache, LocalDate start, LocalDate end) {
        super(cache);
        _startDate = start;
        _endDate = end;
    }
    
    /**
     * constructor for a new instance to be used in loading
     * @param cache
     * @param id
     */
    private AvailabilityInstance(Cache cache, int id) {
        super(cache, id);
    }

    /**
     * setter for the template
     * @param template the template to set
     */
    private synchronized void setTemplate(AvailabilityTemplate template) {
        _template = template;
        getDAO(AvailabilityInstanceDAO.class).update(getModel());
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
    public AvailabilityInstanceModel getModel() {
        AvailabilityInstanceModel model = new AvailabilityInstanceModel();
        model.setId(getID());
        model.setStartDate(_startDate.toDate());
        model.setEndDate(_endDate.toDate());
        model.setTemplateID(_template.getID());
        return model;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        AvailabilityInstanceModel model = getCache().getDAOContext().dao(AvailabilityInstanceDAO.class).uniqueByID(getID()).execute();
        setTemplate(AvailabilityTemplate.load(getCache(), model.getTemplateID())); 
    }
    
    /**
     * initializer to populate a skeleton availability instance
     */
    public void init() {
        AvailabilityInstanceModel model = getCache().getDAOContext().dao(AvailabilityInstanceDAO.class).uniqueByID(getID()).execute();
        _startDate = new LocalDate(model.getStartDate());
        _endDate = new LocalDate(model.getEndDate());
    }
    
    /**
     * load an instance into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param instID the id of the instance to load
     * @return the instance requested
     */
    public static AvailabilityInstance load(Cache cache, int instID) {
        UID uid = new UID(TYPE_IDENTIFIER, instID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityInstance.class); 
        else {
            AvailabilityInstance instance = new AvailabilityInstance(cache, instID);
            cache.cache(uid, instance);
            instance.loadAllChildren();
            instance.init();
            return instance;
        }
    }
    
    /**
     * create a new availability instance using the requested parameters
     * @param businessID the id of the business context to create this in
     * @param template
     * @param start
     * @param end
     * @return the new instance
     */
    public static AvailabilityInstance create(int businessID, AvailabilityTemplate template, LocalDate start, LocalDate end) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityInstance inst = new AvailabilityInstance(cache, start, end);
        AvailabilityInstanceDAO dao = inst.getDAO(AvailabilityInstanceDAO.class);
        inst.setID(dao.getNextID());
        inst.setTemplate(template);
        dao.add(inst.getModel()).enqueue();
        cache.cache(new UID(inst), inst);
        return inst;
    }

}
