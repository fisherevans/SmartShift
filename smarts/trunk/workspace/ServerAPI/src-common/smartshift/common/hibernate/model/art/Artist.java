package smartshift.common.hibernate.model.art;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fevans
 * @version Sept 18, 2014
 * 
 *          class representing an artist
 */
@Entity
@Table(name = "artists")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Artist {
	@Id
	@GeneratedValue
	@Column(name = "id")
    @SerializedName("artistId")
    @Expose
	private Integer id;

	@Column(name = "artist_name")
    @Expose
	private String artistName;

	@Column(name = "date_born")
	@Type(type = "date")
    @Expose
	private Date dateBorn;

	@Column(name = "date_died")
	@Type(type = "date")
    @Expose
	private Date dateDied;

	@Column(name = "gender")
    @Expose
	private String gender;

	@Column(name = "location_born")
    @Expose
	private String locationBorn;

	@OneToMany(mappedBy = "artist")
    @Expose
	private List<Work> works;

    /**
     * @return the id
     */
	public Integer getId() {
		return id;
	}

    /**
     * set the artist's id
     * 
     * @param id
     *            the id to set
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * @return the artist's name
     */
	public String getArtistName() {
		return artistName;
	}

    /**
     * set the name of the artist
     * 
     * @param artistName
     *            the name to set
     */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

    /**
     * @return the artist's DOB
     */
	public Date getDateBorn() {
		return dateBorn;
	}

    /**
     * set the artist's DOB
     * 
     * @param dateBorn
     *            the date to set
     */
	public void setDateBorn(Date dateBorn) {
		this.dateBorn = dateBorn;
	}

    /**
     * @return the artist's DOD
     */
	public Date getDateDied() {
		return dateDied;
	}

    /**
     * set the artist's DOD
     * 
     * @param dateDied
     *            the date to set
     */
	public void setDateDied(Date dateDied) {
		this.dateDied = dateDied;
	}

    /**
     * @return the artist's gender
     */
	public String getGender() {
		return gender;
	}

    /**
     * set the artist's gender
     * 
     * @param gender
     *            the gender to set
     */
	public void setGender(String gender) {
		this.gender = gender;
	}

    /**
     * @return the artist's birth location
     */
	public String getLocationBorn() {
		return locationBorn;
	}

    /**
     * set the artist's birth location
     * 
     * @param locationBorn
     *            the location to set
     */
	public void setLocationBorn(String locationBorn) {
		this.locationBorn = locationBorn;
	}

    /**
     * @return the artist's works
     */
	public List<Work> getWorks() {
		return works;
	}

    /**
     * set the artist's works
     * 
     * @param works
     *            the works to set
     */
	public void setWorks(List<Work> works) {
		this.works = works;
	}

    /**
     * a string representation of the artist
     */
	@Override
	public String toString() {
		return artistName;
	}

}
