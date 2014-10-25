package smartshift.api.jersey.art;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import smartshift.common.hibernate.HibernateFactory;
import smartshift.common.hibernate.model.art.Artist;
import smartshift.common.hibernate.model.art.Work;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          show data for a particular artist
 */
@Path("/artist/{id}")
public class ShowArtist {
    @Context
    private ServletContext context;

    /**
     * @param stringId
     * @return a String containing all data for the artist
     */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String show(@PathParam("id") String stringId) {
		try {
			Integer id = Integer.parseInt(stringId);
			Session session = HibernateFactory.getSession("smartshift");
			Criteria artistCr = session.createCriteria(Artist.class);
			artistCr.add(Restrictions.eq("id", id));
			Artist artist = (Artist) artistCr.uniqueResult();
			
			String out = "<p><a href=\"" + context.getContextPath() + "/artists\">Go Back</a></p>";
			out += "<h1>" + artist.getArtistName() + "</h1>";
			out += "<p><b>Born On:</b> " + artist.getDateBorn().toString() + "</p>";
			out += "<p><b>Born In:</b> " + artist.getLocationBorn().toString() + "</p>";
			out += "<p><b>Died On:</b> " + artist.getDateDied().toString() + "</p>";
			out += "<p><b>Gender:</b> " + artist.getGender().toString() + "</p>";

			out += "<h2>Works</h2>";
			out += "<ul>";
			for(Work work : artist.getWorks())
				out += "<li>" + work.getName() + "</li>";
			out += "</ul>";

			session.close();
			return out;
		} catch(Exception e) {
			return "<b style='color:red;'>Error! " + e.toString() + "</b>";
		}
	}
}
