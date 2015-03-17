package smartshift.business.cache.bo;

import java.util.List;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityTemplateDAO;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.common.util.UID;
import smartshift.common.util.log4j.SmartLogger;

public class AvailabilityTemplate extends CachedObject {
    public static final String TYPE_IDENTIFIER = "AT";
    
    private static final SmartLogger logger = new SmartLogger(AvailabilityTemplate.class);
        
    private List<Availability> _components;
    private String _name;
    private Employee _owner;
    
    private AvailabilityTemplateModel _model;
    
    public AvailabilityTemplate(Cache cache, String name, Employee owner) {
        super(cache);
        _owner = owner;
        _name = name;
    }
    
    private AvailabilityTemplate(Cache cache, AvailabilityTemplateModel model) {
        this(cache, model.getName(), Employee.load(cache, model.getEmployeeID()));
        _model = model;
    }
    
    public void add(Availability availability) {
        _components.add(availability);
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public int getID() {
        if(_model == null)
            return -1;
        return _model.getId();
    }

    @Override
    public void save() {
        try {
            if(_model != null) {
                _model.setName(_name);
                _model.setEmployeeID(_owner.getID());
                getDAO(AvailabilityTemplateDAO.class).update(_model);
            } else {
                _model = getDAO(AvailabilityTemplateDAO.class).add(_name, _owner.getID()).execute();
                for(Availability a : _components) {
                    a.save();
                }
            }
        } catch (HibernateException e) {
            logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void loadAllChildren() {
        // do nothing
    }
    
    public static AvailabilityTemplate load(Cache cache, int tempID) {
        UID uid = new UID(TYPE_IDENTIFIER, tempID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityTemplate.class); 
        else {
            AvailabilityTemplateModel model = cache.getDAOContext().dao(AvailabilityTemplateDAO.class).uniqueByID(tempID).execute();
            AvailabilityTemplate template = null;
            if(model != null) {
                cache.cache(uid, null);
                template = new AvailabilityTemplate(cache, model);
                cache.cache(uid, template);
            }
            return template;
        }
    }
    
    public static AvailabilityTemplate create(int businessID, String name, Employee owner) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityTemplate temp = new AvailabilityTemplate(cache, name, owner);
        temp.save();
        cache.cache(new UID(temp), temp);
        return temp;
    }

}
