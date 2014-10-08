package smartshift.api.jersey.art;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.dao.ArtistDAO;
import smartshift.common.hibernate.model.art.Artist;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.json.GsonFactory;
import smartshift.common.util.params.SimpleIntegerParam;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A page to access the JSON for an artist
 */
public class ArtistMethods {
	private static Logger logger = Logger.getLogger(ArtistMethods.class);

	@Context
	private ServletContext context;

    /**
     * @param integerParam
     * @return a JSON String holding data for the artist whose ID matches
     *         integerParam
     */
    @Path("/json/artist/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getArtistById(@PathParam("id") SimpleIntegerParam integerParam) throws WebApplicationException {
		logger.debug("Fetching artist for ID: " + integerParam.getOriginalValue());
        Session session = null;
        String json = null;
		try {
            session = HibernateFactory.getSession("smartshift");
            Artist artist = ArtistDAO.getArtistById(integerParam.getInteger(), session);
            json = GsonFactory.toJsonResult(artist);
        } catch(ObjectNotFoundException e) {
            String result = "Artist not found with the ID of " + integerParam.getInteger();
            logger.error(result);
            throw APIResultFactory.getException(Status.BAD_REQUEST, result);
        } catch(Exception e) {
            logger.error("Failed to fetch Artist", e);
            throw APIResultFactory.getException(Status.INTERNAL_SERVER_ERROR);
        } finally {
            logger.error("Closing session...");
            if(session != null)
                session.close();
        }
        return json;
	}
}
