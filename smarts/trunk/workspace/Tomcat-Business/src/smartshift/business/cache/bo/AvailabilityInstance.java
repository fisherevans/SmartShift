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
    
    public AvailabilityInstance(Cache cache, LocalDate start, LocalDate end) {
        super(cache);
        _startDate = start;
        _endDate = end;
    }
    
    private AvailabilityInstance(Cache cache, AvailabilityInstanceModel model) {
        this(cache, new LocalDate(model.getStartDate()), new LocalDate(model.getEndDate()));
        _model = model;
        loadAllChildren();       
    }

    private void setTemplate(AvailabilityTemplate template) {
        _template = template;
    }
    
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public int getID() {
        if(_model != null)
            return _model.getId();
        return -1;
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
            }
        } catch (Exception e) {
            logger.warn("Failed to save the availability instance!", e);
        }
    }

    @Override
    public void loadAllChildren() {
        setTemplate(AvailabilityTemplate.load(getCache(), _model.getTemplateID())); 
    }
    
    public static AvailabilityInstance load(Cache cache, int instID) {
        UID uid = new UID(TYPE_IDENTIFIER, instID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityInstance.class); 
        else {
            AvailabilityInstanceModel model = cache.getDAOContext().dao(AvailabilityInstanceDAO.class).uniqueByID(instID).execute();
            AvailabilityInstance instance = null;
            if(model != null) {
                cache.cache(uid, null);
                instance = new AvailabilityInstance(cache, model);
                cache.cache(uid, instance);
            }
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
