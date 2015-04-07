package smartshift.business.cache.bo;

import java.util.List;
import smartshift.business.hibernate.dao.AvailabilityTemplateDAO;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.common.util.UID;

/**
 * a template from which to create an availability instance
 * @author drew
 */
public class AvailabilityTemplate extends CachedObject {
    /** the type identifier for an availability template */
    public static final String TYPE_IDENTIFIER = "AT";
        
    private List<Availability> _components;
    private String _name;
    private Employee _owner;
    
    /**
     * constructor for a new template used in creation
     * @param cache
     * @param name
     * @param owner
     */
    private AvailabilityTemplate(Cache cache, String name, Employee owner) {
        super(cache);
        _owner = owner;
        _name = name;
    }
    
    /**
     * constructor for a new template used in loading
     * @param cache
     * @param id
     */
    private AvailabilityTemplate(Cache cache, int id) {
        super(cache, id);
    }

    /**
     * an availability has been added to the template
     * @param availability the availability to add
     */
    synchronized void addedComponent(Availability availability) {
        _components.add(availability);
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
    public AvailabilityTemplateModel getModel() {
        AvailabilityTemplateModel model = new AvailabilityTemplateModel();
        model.setId(getID());
        model.setName(_name);
        model.setEmployeeID(_owner.getID());
        return model;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        // do nothing
    }
    
    /**
     * load a template into memory (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param tempID the id of the template to load
     * @return the template requested
     */
    public static AvailabilityTemplate load(Cache cache, int tempID) {
        UID uid = new UID(TYPE_IDENTIFIER, tempID);
        if(cache.contains(uid))
            return cache.getCached(uid, AvailabilityTemplate.class); 
        else {
            AvailabilityTemplate template = new AvailabilityTemplate(cache, tempID);
            cache.cache(uid, template);
            template.loadAllChildren();
            template.initialize();
            return template;
        }
    }
    
    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        AvailabilityTemplateModel model = getCache().getDAOContext().dao(AvailabilityTemplateDAO.class).uniqueByID(getID()).execute();
        _owner = Employee.load(getCache(), model.getEmployeeID());
        _name = model.getName();
    }
    
    /**
     * create a new availability template with the given parameters
     * @param businessID the id of the business to create the template for
     * @param name the name of the template
     * @param owner the employee who owns the template
     * @return the new template
     */
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
