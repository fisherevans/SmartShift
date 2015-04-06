package smartshift.business.cache.bo;

import java.util.List;
import org.hibernate.HibernateException;
import smartshift.business.hibernate.dao.AvailabilityInstanceDAO;
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
    
    private AvailabilityTemplate(Cache cache, String name, Employee owner) {
        super(cache);
        _owner = owner;
        _name = name;
    }
    
    private AvailabilityTemplate(Cache cache, int id) {
        super(cache, id);
    }

    synchronized void addedComponent(Availability availability) {
        _components.add(availability);
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }
    
    public AvailabilityTemplateModel getModel() {
        AvailabilityTemplateModel model = new AvailabilityTemplateModel();
        model.setId(getID());
        model.setName(_name);
        model.setEmployeeID(_owner.getID());
        return model;
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
            AvailabilityTemplate template = new AvailabilityTemplate(cache, tempID);
            cache.cache(uid, template);
            template.loadAllChildren();
            template.init();
            return template;
        }
    }
    
    public void init() {
        AvailabilityTemplateModel model = getCache().getDAOContext().dao(AvailabilityTemplateDAO.class).uniqueByID(getID()).execute();
        _owner = Employee.load(getCache(), model.getEmployeeID());
        _name = model.getName();
    }
    
    public static AvailabilityTemplate create(int businessID, String name, Employee owner) {
        Cache cache = Cache.getCache(businessID);
        AvailabilityTemplate temp = new AvailabilityTemplate(cache, name, owner);
        AvailabilityTemplateDAO dao = temp.getDAO(AvailabilityTemplateDAO.class);
        temp.setID(dao.getNextID());
        dao.add(temp.getModel()).enqueue();
        cache.cache(new UID(temp), temp);
        return temp;
    }

}
