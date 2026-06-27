package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "troupes")
public class Troupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "logo_url")
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

        if (!artists.contains(artist)) {
            artists.add(artist);
            artist.setTroupe(this);
        }

        return this;
    }

    public Troupe removeArtist(Artist artist) {

        if (artists.contains(artist)) {
            artists.remove(artist);

            if (artist.getTroupe() != null &&
                    artist.getTroupe().equals(this)) {

                artist.setTroupe(null);
            }
        }

        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}