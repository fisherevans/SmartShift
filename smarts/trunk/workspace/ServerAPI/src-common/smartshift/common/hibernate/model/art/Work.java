package smartshift.common.hibernate.model.art;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import smartshift.common.util.json.JsonEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author fevans
 * @version Sept 18, 2014
 */
@Entity
@Table(name = "works")
public class Work extends JsonEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	@Expose
	@SerializedName("workId")
	private Integer id;

	@Column(name = "name")
	@Expose
	private String name;

	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;

    /**
     * @return the work's id
     */
	public Integer getId() {
		return id;
	}

    /**
     * set the work's id
     * 
     * @param id
     *            the id to set
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * @return the work's name
     */
	public String getName() {
		return name;
	}

    /**
     * set the work's name
     * 
     * @param name
     *            the name to set
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * @return the work's artist
     */
	public Artist getArtist() {
		return this.artist;
	}
	
    /**
     * set the work's artist
     * 
     * @param artist
     *            the artist to set
     */
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
