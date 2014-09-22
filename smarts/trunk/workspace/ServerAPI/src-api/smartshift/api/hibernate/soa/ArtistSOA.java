package smartshift.api.hibernate.soa;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import smartshift.api.hibernate.art.Artist;

public class ArtistSOA {
	
	/**
	 * Find an artist by its primary id
	 * @param id The primary ID
	 * @param session The hibernate session to use
	 * @return The unique artist. Null if not found
	 */
	public static Artist getArtistById(Integer id, Session session) {
		Criteria artistCr = session.createCriteria(Artist.class);
		artistCr.add(Restrictions.eq("id", id));
		return (Artist) artistCr.uniqueResult();
	}

	/**
	 * Fetch a list of all artists in the database
	 * @param session The hibernate session to use
	 * @return The list of artists.
	 */
	public static List<Artist> getAllArtists(Session session) {
		Criteria artistCr = session.createCriteria(Artist.class);
		return (List<Artist>) artistCr.list();
	}
	
}
