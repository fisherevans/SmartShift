package smartshift.business.cache.bo;

import org.joda.time.LocalDate;
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
    
    private AvailabilityInstanceModel _model;
    
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

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setStartDate(_startDate.toDate());
                _model.setEndDate(_endDate.toDate());
                _model.setTemplateID(_template.getID());
                getDAO(AvailabilityInstanceDAO.class).update(_model);
            } else {
                _template.save();
                _model = getDAO(AvailabilityInstanceDAO.class).add(_template.getID(), _startDate.toDate(), _endDate.toDate()).execute();
                setID(_model.getId());
            }
        } catch (Exception e) {
            logger.warn("Failed to save the availability instance!", e);
        }
    }

    @Override
    public void loadAllChildren() {
        setTemplate(AvailabilityTemplate.load(getCache(), _model.getTemplateID())); 
    }
    
    public void init() {
        AvailabilityInstanceModel model = getCache().getDAOContext().dao(AvailabilityInstanceDAO.class).uniqueByID(getID()).execute();
        _model = model;
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
        inst.setTemplate(template);
        inst.save();
        cache.cache(new UID(inst), inst);
        return inst;
    }

}
