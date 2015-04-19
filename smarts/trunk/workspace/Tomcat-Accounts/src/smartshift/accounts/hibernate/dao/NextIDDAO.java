package smartshift.accounts.hibernate.dao;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.criterion.Restrictions;
import smartshift.accounts.hibernate.model.NextIDModel;
import smartshift.common.hibernate.DBAction;
import smartshift.common.hibernate.dao.tasks.criteria.UniqueByCriteriaTask;
import smartshift.common.util.log4j.SmartLogger;

/**
 * The data access object for the Server Object
 * @author D. Fisher Evans <contact@fisherevans.com>
 *
 */
public class NextIDDAO extends BaseAccountsDAO<NextIDModel> {
    private static final SmartLogger logger = new SmartLogger(NextIDDAO.class);
    
    private Map<String, Integer> _nextIDs = new HashMap<String, Integer>();
    
    /**
     * Initializes the object.
     */
    public NextIDDAO() {
        super(NextIDModel.class);
    }
    
    /**
     * gets the task that finds a unique next id model by name
     * @param name the name
     * @return the task object
     */
    public UniqueByCriteriaTask<NextIDModel> uniqueByName(String name) {
        return uniqueByCriteria(Restrictions.eq("name", name));
    }
    
    /**
     * gets the next id and increments (in it's on cache)
     * @param name the name of the id you're looking for
     * @return the id to use, null if there was an error
     */
    public Integer getNextID(String name) {
        Integer result = null;
        synchronized(_nextIDs) {
            try {
                Integer nextID = _nextIDs.get(name);
                logger.debug("NextID for " + name + " = " + nextID);
                if(nextID == null) {
                    logger.debug("NextID was not cached for " + name + " - fetching");
                    NextIDModel model = uniqueByName(name).execute();
                    if(model == null) {
                        logger.debug("NextID was null for " + name + " - adding");
                        model = new NextIDModel();
                        model.setName(name);
                        add(model);
                    }
                    nextID = model.getNextID();
                    logger.debug("NextID cached for " + name + " is " + nextID);
                }
                _nextIDs.put(name, nextID + 1);
                result = nextID;
            } catch(Exception e) {
                logger.error("Failed to get next id for " + name, e);
            }
        }
        return result;
    }
    
    /**
     * Flushes the currently cached IDs to the DB
     */
    public void saveNextIDs() {
        synchronized(_nextIDs) {
            DBAction action = new DBAction(getSession());
            try {
                NextIDModel model;
                for(String name:_nextIDs.keySet()) {
                    Integer nextID = _nextIDs.get(name);
                    logger.debug("Updating " + name + " with " + nextID);
                    model = uniqueByName(name).executeWithSession(action.session());
                    model.setNextID(nextID);
                    update(model).executeWithSession(action.session());
                }
            } catch(Exception e) {
                action.rolback();
                logger.fatal("Failed to flush cached IDs to DB!!!!", e);
                return;
            }
            logger.info("NextIDs successfully flushed to DB");
            action.commit();
        }
    }

    @Override
    protected SmartLogger getLogger() {
        return logger;
    }
}
