package smartshift.common.hibernate;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import smartshift.common.util.log4j.SmartLogger;

public class DynamicNextID {
    private static final SmartLogger logger = new SmartLogger(DynamicNextID.class);
    
    private static Map<DAOContext, DynamicNextID> instances = new HashMap<>();
    
    private DAOContext _daoContext;
    
    private Map<Class, Integer> nextIDs = new HashMap<>();
    
    public DynamicNextID(DAOContext daoContext) {
        _daoContext = daoContext;
    }
    
    public Integer getNextID(Class model) {
        return getNextID(model, "id");
    }
    
    public Integer getNextID(Class model, String column) {
        Integer nextID = nextIDs.get(model);
        if(nextID == null)
            nextID = getMaxDatabaseID(_daoContext, model, column);
        nextIDs.put(model, nextID + 1);
        return nextID;
    }
    
    public static DynamicNextID getInstance(DAOContext daoContext) {
        DynamicNextID instance = instances.get(daoContext);
        if(instance == null) {
            instance = new DynamicNextID(daoContext);
            instances.put(daoContext, instance);
        }
        return instance;
    }
    
    public static Integer getMaxDatabaseID(DAOContext daoContext, Class model, String column) {
        DBAction dbAction = new DBAction(daoContext.getSession());
        Criteria criteria = dbAction.session()
                .createCriteria(model)
                .setProjection(Projections.max(column));
        return (Integer) criteria.uniqueResult();
    }
}
