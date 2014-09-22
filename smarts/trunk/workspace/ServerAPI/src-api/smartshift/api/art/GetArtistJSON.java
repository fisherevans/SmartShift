package smartshift.api.art;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import smartshift.api.JsonResult;
import smartshift.api.hibernate.HibernateFactory;
import smartshift.api.hibernate.art.Artist;
import smartshift.api.hibernate.soa.ArtistSOA;
import smartshift.api.util.APIResultUtil;
import smartshift.api.util.params.SimpleIntegerParam;

@Path("/json/artist/{id}")
public class GetArtistJSON {
	private static Logger logger = Logger.getLogger(GetArtistJSON.class);

	@Context
	private ServletContext context;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String show(@PathParam("id") SimpleIntegerParam integerParam) {
		logger.debug("Fetching artist for ID: " + integerParam.getOriginalValue());
		try {
			Session session = HibernateFactory.getSession("smartshift");
			Artist artist = ArtistSOA.getArtistById(integerParam.getInteger(), session);
			String json = new JsonResult(artist).toJson();
			session.close();
			return json;
		} catch (Exception e) {
			logger.error("Failed to fetch Artist", e);
			throw APIResultUtil.getInternalErrorException();
		}
	}
}
