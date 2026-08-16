package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "artists")
public class Artist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "The firstname must not be empty.")
	@Size(min = 2, max = 60, message = "The firstname must be between 2 and 60 characters long.")
	private String firstname;

	@NotEmpty(message = "The lastname must not be empty.")
	@Size(min = 2, max = 60, message = "The lastname must be between 2 and 60 characters long.")
	private String lastname;

	@ManyToOne
	@JoinColumn(name = "troupe_id")
	private Troupe troupe;

	@ManyToMany(mappedBy = "artists")
	private List<Type> types = new ArrayList<>();

	@OneToMany(mappedBy = "artist")
	private List<ArtistLanguage> artistLanguages = new ArrayList<>();

	protected Artist() {
	}

	public Artist(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Troupe getTroupe() {
		return troupe;
	}

	public void setTroupe(Troupe troupe) {
		this.troupe = troupe;
	}

	public List<Type> getTypes() {
		return types;
	}

	public List<ArtistLanguage> getArtistLanguages() { return artistLanguages; }

	public Artist addType(Type type) {
		if (!this.types.contains(type)) {
			this.types.add(type);
			type.addArtist(this);
		}

		return this;
	}

	public Artist removeType(Type type) {
		if (this.types.contains(type)) {
			this.types.remove(type);
			type.getArtists().remove(this);
		}

		return this;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
}
