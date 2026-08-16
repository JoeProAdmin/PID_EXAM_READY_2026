package be.iccbxl.pid.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class TarifServiceTest {

    @Test
    void exposesTheThreeExpectedTypes() {
        TarifService service = new TarifService(mock(TarifRepository.class), mock(ShowRepository.class));

        assertEquals(3, service.getTypes().size());
        assertEquals("promo", service.getTypes().get(0));
    }

    @Test
    void createsAValidSpecialTarif() {
        TarifRepository tarifRepository = mock(TarifRepository.class);
        ShowRepository showRepository = mock(ShowRepository.class);
        TarifService service = new TarifService(tarifRepository, showRepository);
        Show show = new Show();
        Long showId = Long.valueOf(1L);

        when(showRepository.findById(showId)).thenReturn(Optional.of(show));
        when(tarifRepository.save(any(Tarif.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tarif tarif = service.create(showId, "senior", 7.50);

        assertEquals("senior", tarif.getType());
        assertEquals(7.50, tarif.getPrix());
    }

    @Test
    void rejectsAnUnknownType() {
        TarifRepository tarifRepository = mock(TarifRepository.class);
        ShowRepository showRepository = mock(ShowRepository.class);
        TarifService service = new TarifService(tarifRepository, showRepository);
        Long showId = Long.valueOf(1L);
        when(showRepository.findById(showId)).thenReturn(Optional.of(new Show()));

        assertThrows(IllegalArgumentException.class, () -> service.create(showId, "student", 5.0));
    }
}
