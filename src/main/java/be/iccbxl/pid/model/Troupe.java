package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "troupes")
public class Troupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la troupe est obligatoire.")
    @Size(max = 60, message = "Le nom de la troupe ne peut pas dépasser 60 caractères.")
    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Size(max = 255, message = "L'URL du logo ne peut pas dépasser 255 caractères.")
    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @OneToMany(mappedBy = "troupe")
    private List<Artist> artists = new ArrayList<>();

    protected Troupe() {
    }

    public Troupe(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Troupe addArtist(Artist artist) {
        if (artist != null && !artists.contains(artist)) {
            artists.add(artist);
            artist.setTroupe(this);
        }
        return this;
    }

    public Troupe removeArtist(Artist artist) {
        if (artist != null && artists.remove(artist)
                && this.equals(artist.getTroupe())) {
            artist.setTroupe(null);
        }
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
