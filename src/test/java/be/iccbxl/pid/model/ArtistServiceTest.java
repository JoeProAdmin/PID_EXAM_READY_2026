package be.iccbxl.pid.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class ArtistServiceTest {

    @Test
    void affiliatesAnArtistToAnExistingTroupe() {
        ArtistRepository artistRepository = mock(ArtistRepository.class);
        TroupeRepository troupeRepository = mock(TroupeRepository.class);
        ArtistService service = new ArtistService(artistRepository, troupeRepository);
        Artist artist = new Artist("Alice", "Martin");
        Troupe troupe = new Troupe("Collectif Scène", "https://example.org/logo.png");
        Long artistId = Long.valueOf(1L);
        Long troupeId = Long.valueOf(2L);

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(troupeRepository.findById(troupeId)).thenReturn(Optional.of(troupe));
        when(artistRepository.save(any(Artist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Artist result = service.affiliateToTroupe(artistId, troupeId);

        assertSame(troupe, result.getTroupe());
    }

    @Test
    void removesAnArtistAffiliation() {
        ArtistRepository artistRepository = mock(ArtistRepository.class);
        TroupeRepository troupeRepository = mock(TroupeRepository.class);
        ArtistService service = new ArtistService(artistRepository, troupeRepository);
        Artist artist = new Artist("Alice", "Martin");
        Long artistId = Long.valueOf(1L);
        artist.setTroupe(new Troupe("Compagnie du Centre", null));

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(artistRepository.save(any(Artist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Artist result = service.removeFromTroupe(artistId);

        assertNull(result.getTroupe());
    }
}