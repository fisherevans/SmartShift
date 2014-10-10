package smartshift.api.jersey.art;

import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import smartshift.common.hibernate.model.art.Artist;
import smartshift.common.util.hibernate.GenericHibernateUtil;
import smartshift.common.util.json.GsonFactory;
import smartshift.common.util.params.SimpleIntegerParam;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A page to access the JSON for an artist
 */
@Path("/json/artist/")
public class ArtistMethods {
	private static Logger logger = Logger.getLogger(ArtistMethods.class);

	@Context
	private ServletContext context;

    /**
     * Gets the artist corresponding to an id
     * 
     * @param integerParam id of artist to lookup
     * @return a JSON String holding data for the artist whose ID matches
     * @throws WebApplicationException
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getArtist(@PathParam("id") SimpleIntegerParam integerParam) throws WebApplicationException {
        logger.debug("Fetching artist for ID: " + integerParam.getInteger());
        Object artist = GenericHibernateUtil.getUniqueObjectJson(Artist.class, integerParam.getInteger());
        return GsonFactory.toJsonResult(artist);
    }

    /**
     * Returns all artists as an array
     * 
     * @return a JSON String holding all artists
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getArtists() throws WebApplicationException {
        List<Object> artists = GenericHibernateUtil.getObjectListJson(Artist.class);
        return GsonFactory.toJsonResult(artists);
    }
}
