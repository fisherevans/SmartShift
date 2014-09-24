package smartshift.api.art;

import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import smartshift.api.hibernate.HibernateFactory;
import smartshift.api.hibernate.art.Artist;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          A page listing all artists
 */
@Path("/artists")
public class ListArtists {
    @Context
    private ServletContext context;

    /**
     * @return a String listing all artists
     */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String list() {
		Session session = HibernateFactory.getSession("smartshift");
		Criteria cr = session.createCriteria(Artist.class);
        List<Artist> artists = (List<Artist>) cr.list();

		String text = "";
		String out = "<h1>Artists!</h1>";
		out += "<ul>";
		for(Artist artist : artists) {
			out += "<li>";
			out += "<a href=\"" + context.getContextPath() + "/artist/" + artist.getId() + "\">";
			out += artist.toString() + " - " + artist.getWorks().size() + " Works";
			out += "</a></li>";
			text += "[" + artist.toString() + " - " + artist.getWorks().size() + " works] ";
		}
		out += "</ul>";
		
		session.close();
		return out;
	}
}
