package be.iccbxl.pid.model;
import java.util.*; import javax.persistence.*; import javax.validation.constraints.*;
@Entity @Table(name="languages") public class Language {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Size(max=60) @Column(nullable=false,unique=true,length=60) private String language;
 @OneToMany(mappedBy="language") private List<ArtistLanguage> artistLanguages=new ArrayList<>();
 protected Language(){} public Language(String language){this.language=language;}
 public Long getId(){return id;} public String getLanguage(){return language;} public void setLanguage(String language){this.language=language;}
 public List<ArtistLanguage> getArtistLanguages(){return artistLanguages;} public String toString(){return language;}
}
