package be.iccbxl.pid.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.slugify.Slugify;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String slug;
    private String title;
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = true)
    private Location location;

    private boolean bookable;
    private double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(targetEntity = Representation.class, mappedBy = "show")
    private List<Representation> representations = new ArrayList<>();

    @OneToMany(targetEntity = Video.class, mappedBy = "show")
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "show")
    private List<Tarif> tarifs = new ArrayList<>();

    @ManyToMany(mappedBy = "shows")
    private List<ArtistType> artistTypes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "show_tag",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    public Show() {
    }

    public Show(String title, String description, String posterUrl, Location location,
                boolean bookable, double price) {
        this.slug = new Slugify().slugify(title);
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.location = location;
        this.bookable = bookable;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getSlug() { return slug; }
    private void setSlug(String slug) { this.slug = slug; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; setSlug(new Slugify().slugify(title)); }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public Location getLocation() { return location; }

    public void setLocation(Location location) {
        if (this.location != null) {
            this.location.removeShow(this);
        }
        this.location = location;
        if (location != null) {
            location.addShow(this);
        }
    }

    public boolean isBookable() { return bookable; }
    public void setBookable(boolean bookable) { this.bookable = bookable; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<Representation> getRepresentations() { return representations; }
    public List<Video> getVideos() { return videos; }
    public List<Tarif> getTarifs() { return tarifs; }
    public List<ArtistType> getArtistTypes() { return artistTypes; }
    public List<Tag> getTags() { return tags; }

    public Show addRepresentation(Representation representation) {
        if (!representations.contains(representation)) { representations.add(representation); representation.setShow(this); }
        return this;
    }
    public Show removeRepresentation(Representation representation) { representations.remove(representation); return this; }
    public Show addVideo(Video video) {
        if (!videos.contains(video)) { videos.add(video); video.setShow(this); }
        return this;
    }
    public Show removeVideo(Video video) {
        if (videos.remove(video) && this.equals(video.getShow())) { video.setShow(null); }
        return this;
    }
    public Show addTarif(Tarif tarif) {
        if (tarif != null && !tarifs.contains(tarif)) { tarifs.add(tarif); tarif.setShow(this); }
        return this;
    }
    public Show removeTarif(Tarif tarif) {
        if (tarifs.remove(tarif) && this.equals(tarif.getShow())) { tarif.setShow(null); }
        return this;
    }
    public Show addArtistType(ArtistType artistType) {
        if (!artistTypes.contains(artistType)) { artistTypes.add(artistType); artistType.addShow(this); }
        return this;
    }
    public Show removeArtistType(ArtistType artistType) {
        if (artistTypes.remove(artistType)) { artistType.getShows().remove(this); }
        return this;
    }
    public Show addTag(Tag tag) {
        if (tag != null && !tags.contains(tag)) { tags.add(tag); tag.addShow(this); }
        return this;
    }
    public Show removeTag(Tag tag) {
        if (tags.remove(tag)) { tag.removeShow(this); }
        return this;
    }

    @Override
    public String toString() {
        return "Show [id=" + id + ", title=" + title + "]";
    }
}
