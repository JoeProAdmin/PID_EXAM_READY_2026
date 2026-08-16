package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final TroupeRepository troupeRepository;

    public ArtistService(ArtistRepository artistRepository,
                         TroupeRepository troupeRepository) {
        this.artistRepository = artistRepository;
        this.troupeRepository = troupeRepository;
    }

    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        artistRepository.findAll().forEach(artists::add);
        return artists;
    }

    public Artist getArtist(String id) {
        try {
            return artistRepository.findById(Long.parseLong(id));
        } catch (RuntimeException exception) {
            return null;
        }
    }

    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void updateArtist(Long id, Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    /**
     * Affilie un artiste à une troupe. Une troupe inexistante ou un artiste
     * inexistant est explicitement refusé.
     */
    public Artist affiliateToTroupe(Long artistId, Long troupeId) {
        if (troupeId == null) {
            return removeFromTroupe(artistId);
        }

        Artist artist = getExistingArtist(artistId);
        Troupe troupe = troupeRepository.findById(troupeId).orElse(null);

        if (troupe == null) {
            throw new IllegalArgumentException("La troupe sélectionnée n'existe pas.");
        }

        artist.setTroupe(troupe);
        return artistRepository.save(artist);
    }

    /**
     * Retire l'affiliation de l'artiste. L'artiste doit exister.
     */
    public Artist removeFromTroupe(Long artistId) {
        Artist artist = getExistingArtist(artistId);
        artist.setTroupe(null);
        return artistRepository.save(artist);
    }

    private Artist getExistingArtist(Long artistId) {
        Artist artist = artistId == null ? null : artistRepository.findById(artistId).orElse(null);

        if (artist == null) {
            throw new IllegalArgumentException("L'artiste sélectionné n'existe pas.");
        }

        return artist;
    }
}
