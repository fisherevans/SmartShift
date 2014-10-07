package smartshift.common.hibernate.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import smartshift.common.hibernate.model.art.Artist;

/**
 * @author fevans
 * @version Sept 18, 2014
 */
public class ArtistDAO {
	
	/**
	 * Find an artist by its primary id
	 * @param id The primary ID
	 * @param session The hibernate session to use
	 * @return The unique artist. Null if not found
	 */
	public static Artist getArtistById(Integer id, Session session) {
        return (Artist) session.load(Artist.class, id);
	}

	/**
	 * Fetch a list of all artists in the database
	 * @param session The hibernate session to use
	 * @return The list of artists.
	 */
	public static List<Artist> getAllArtists(Session session) {
		Criteria artistCr = session.createCriteria(Artist.class);
		return artistCr.list();
	}
	
}
