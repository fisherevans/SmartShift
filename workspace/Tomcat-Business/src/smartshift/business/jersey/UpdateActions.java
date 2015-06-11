package smartshift.business.jersey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.updates.BaseUpdate;
import smartshift.business.updates.UpdateComparator;
import smartshift.business.updates.UpdateManager;
import smartshift.common.util.log4j.SmartLogger;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * jersey actions for employees
 */
@Provider
@Path("/updates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UpdateActions extends BaseBusinessActions {
    private static final SmartLogger logger = new SmartLogger(UpdateActions.class);
    
    /** gets un fetched updates
     * @return the update json object
     */
    @GET
    public Response getUpdates() {
    	logger.debug("getUpdates() Enter");
    	UpdateManager.debugPrintAll();
    	Set<BaseUpdate> updates = getUpdateManager().getSessionUpdates(getUserSession().sessionID, true);
    	List<BaseUpdate> sortedUpdates = new ArrayList<>();
    	sortedUpdates.addAll(updates);
    	Collections.sort(sortedUpdates, UpdateComparator.instance());
        logger.debug("getUpdates() Got " + sortedUpdates.size());
    	List<Object> json = new ArrayList<>(sortedUpdates.size());
    	for(BaseUpdate update:sortedUpdates)
    	    json.add(update.getJSONMap());
        logger.debug("getUpdates() Returning updates");
        return getObjectResponse(Status.OK, json);
    }
}
