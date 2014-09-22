package smartshift.api.hibernate.art;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import smartshift.api.util.JsonEntity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "artists")
public class Artist extends JsonEntity {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public Date getDateBorn() {
		return dateBorn;
	}

	public void setDateBorn(Date dateBorn) {
		this.dateBorn = dateBorn;
	}

	public Date getDateDied() {
		return dateDied;
	}

	public void setDateDied(Date dateDied) {
		this.dateDied = dateDied;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocationBorn() {
		return locationBorn;
	}

	public void setLocationBorn(String locationBorn) {
		this.locationBorn = locationBorn;
	}

	public List<Work> getWorks() {
		return works;
	}

	public void setWorks(List<Work> works) {
		this.works = works;
	}

	@Override
	public String toString() {
		return artistName;
	}

}
