package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le mot-clé est obligatoire.")
    @Size(max = 30, message = "Le mot-clé ne peut pas dépasser 30 caractères.")
    @Column(nullable = false, unique = true, length = 30)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private List<Show> shows = new ArrayList<>();

    protected Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Show> getShows() {
        return shows;
    }

    public Tag addShow(Show show) {
        if (show != null && !shows.contains(show)) {
            shows.add(show);
        }
        return this;
    }

    public Tag removeShow(Show show) {
        shows.remove(show);
        return this;
    }

    @Override
    public String toString() {
        return tag;
    }
}
