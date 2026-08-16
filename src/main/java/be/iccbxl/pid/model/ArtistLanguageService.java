package be.iccbxl.pid.model;
import java.util.*; import org.springframework.stereotype.Service;
@Service public class ArtistLanguageService {
 private static final List<String> LEVELS=Arrays.asList("LANGUE_MATERNELLE","DEBUTANT","INTERMEDIAIRE","COURANT");
 private final ArtistRepository artists; private final LanguageRepository languages; private final ArtistLanguageRepository repository;
 public ArtistLanguageService(ArtistRepository a,LanguageRepository l,ArtistLanguageRepository r){artists=a;languages=l;repository=r;}
 public List<String> getLevels(){return LEVELS;} public List<Language> getLanguages(){List<Language> r=new ArrayList<>();languages.findAll().forEach(r::add);return r;}
 public ArtistLanguage add(Long artistId,Long languageId,String level){Artist a=artistId==null?null:artists.findById(artistId).orElse(null); Language l=languageId==null?null:languages.findById(languageId).orElse(null); if(a==null||l==null)throw new IllegalArgumentException("L'artiste ou la langue n'existe pas."); if(!isActor(a))throw new IllegalArgumentException("Seuls les artistes de type comédien peuvent recevoir une langue."); if(!LEVELS.contains(level))throw new IllegalArgumentException("Le niveau est invalide."); if(repository.existsByArtistAndLanguage(a,l))throw new IllegalArgumentException("Cette langue est déjà associée à cet artiste."); return repository.save(new ArtistLanguage(a,l,level));}
 public boolean isActor(Artist a){return a.getTypes().stream().anyMatch(t->"comédien".equalsIgnoreCase(t.getType()));}
 public List<ArtistLanguage> fluent(String language){for(Language l:getLanguages())if(l.getLanguage().equalsIgnoreCase(language))return repository.findByLanguageAndLevel(l,"COURANT");return Collections.emptyList();}
}
