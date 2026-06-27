package be.iccbxl.pid.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le titre est obligatoire.")
    private String title;

    @NotEmpty(message = "L'URL est obligatoire.")
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    protected Video() {
    }

    public Video(String title, String videoUrl, Show show) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.show = show;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return title;
    }
}