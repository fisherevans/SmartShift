package smartshift.api.art;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import smartshift.api.hibernate.HibernateUtil;
import smartshift.api.hibernate.art.Artist;

@Path("/artists")
public class ListArtists {
    @Context
    private ServletContext context;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria cr = session.createCriteria(Artist.class);
		List<Artist> artists = (List<Artist>)cr.list();

		String out = "<h1>Artists!</h1>";
		out += "<ul>";
		for(Artist artist : artists) {
			out += "<li>";
			out += "<a href=\"" + context.getContextPath() + "/artist/" + artist.getId() + "\">";
			out += artist.toString() + " - " + artist.getWorks().size() + " Works";
			out += "</a></li>";
		}
		out += "</ul>";
		
		session.close();
		return out;
	}
}
