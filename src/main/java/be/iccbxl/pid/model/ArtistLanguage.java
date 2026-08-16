package be.iccbxl.pid.model;
import javax.persistence.*; import javax.validation.constraints.*;
@Entity @Table(name="artist_language",uniqueConstraints=@UniqueConstraint(columnNames={"artist_id","language_id"})) public class ArtistLanguage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne @JoinColumn(name="artist_id",nullable=false) private Artist artist;
 @ManyToOne @JoinColumn(name="language_id",nullable=false) private Language language;
 @NotBlank @Size(max=30) @Column(nullable=false,length=30) private String level;
 protected ArtistLanguage(){} public ArtistLanguage(Artist a,Language l,String level){artist=a;language=l;this.level=level;}
 public Long getId(){return id;} public Artist getArtist(){return artist;} public Language getLanguage(){return language;} public String getLevel(){return level;} public void setArtist(Artist a){artist=a;} public void setLanguage(Language l){language=l;} public void setLevel(String l){level=l;}
}
