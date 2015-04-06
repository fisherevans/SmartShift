package smartshift.business.cache.bo;

import org.joda.time.LocalDate;
import smartshift.business.hibernate.dao.AvailabilityDAO;
import smartshift.business.hibernate.dao.AvailabilityInstanceDAO;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class AvailabilityInstance extends CachedObject {
    public static final String TYPE_IDENTIFIER = "AI";
    
    private static final SmartLogger logger = new SmartLogger(AvailabilityInstance.class);
    
    private AvailabilityTemplate _template;
    private LocalDate _startDate;
    private LocalDate _endDate;
    
    private AvailabilityInstance(Cache cache, LocalDate start, LocalDate end) {
        super(cache);
        _startDate = start;
        _endDate = end;
    }
    
    private AvailabilityInstance(Cache cache, int id) {
        super(cache, id);
    }

    private void setTemplate(AvailabilityTemplate template) {
        _template = template;
    }
    
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    public AvailabilityInstanceModel getModel() {
        AvailabilityInstanceModel model = new AvailabilityInstanceModel();
        model.setId(getID());
        model.setStartDate(_startDate.toDate());
        model.setEndDate(_endDate.toDate());
        model.setTemplateID(_template.getID());
        return model;
    }

    @Override
    public void loadAllChildren() {
        AvailabilityInstanceModel model = getCache().getDAOContext().dao(AvailabilityInstanceDAO.class).uniqueByID(getID()).execute();
        setTemplate(AvailabilityTemplate.load(getCache(), model.getTemplateID())); 
    }
    
    public void init() {
        AvailabilityInstanceModel model = getCache().getDAOContext().dao(AvailabilityInstanceDAO.class).uniqueByID(getID()).execute();
        _startDate = new LocalDate(model.getStartDate());
        _endDate = new LocalDate(model.getEndDate());
    }
    
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
