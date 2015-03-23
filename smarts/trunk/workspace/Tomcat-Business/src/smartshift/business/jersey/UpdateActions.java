package smartshift.business.jersey;

import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import smartshift.business.jersey.objects.UpdatesJSON;
import smartshift.business.updates.BaseUpdate;
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
        logger.debug("getUpdates() Got " + updates.size());
    	UpdatesJSON json = new UpdatesJSON();
    	for(BaseUpdate update:updates)
    	    json.addUpdate(update);
        logger.debug("getUpdates() Returning updates");
        return getObjectResponse(Status.OK, json);
    }
}
