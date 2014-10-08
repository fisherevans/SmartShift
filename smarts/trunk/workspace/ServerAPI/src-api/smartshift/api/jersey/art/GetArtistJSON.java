package smartshift.api.jersey.art;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.dao.ArtistDAO;
import smartshift.common.hibernate.model.art.Artist;
import smartshift.common.util.json.APIResultFactory;
import smartshift.common.util.json.JsonResult;
import smartshift.common.util.params.SimpleIntegerParam;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A page to access the JSON for an artist
 */
@Path("/json/artist/{id}")
public class GetArtistJSON {
	private static Logger logger = Logger.getLogger(GetArtistJSON.class);

	@Context
	private ServletContext context;

    /**
     * @param integerParam
     * @return a JSON String holding data for the artist whose ID matches
     *         integerParam
     */
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	public String show(@PathParam("id") SimpleIntegerParam integerParam) {
		logger.debug("Fetching artist for ID: " + integerParam.getOriginalValue());
		try {
			Session session = HibernateFactory.getSession("smartshift");
            Artist artist = ArtistDAO.getArtistById(integerParam.getInteger(), session);
            String json = JsonResult.create(artist);
			session.close();
			return json;
		} catch (Exception e) {
			logger.error("Failed to fetch Artist", e);
			throw APIResultFactory.getInternalErrorException();
		}
	}
}
