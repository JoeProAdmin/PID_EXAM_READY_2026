package be.iccbxl.pid.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class RepresentationServiceTest {

    @Test
    void detectsAnOccupiedRoomAtTheSameMoment() {
        RepresentationRepository repository = mock(RepresentationRepository.class);
        RepresentationService service = new RepresentationService(repository);
        Room room = mock(Room.class);
        LocalDateTime when = LocalDateTime.of(2026, 9, 2, 10, 0);

        when(repository.existsByRoomAndWhen(room, when)).thenReturn(true);

        assertTrue(service.isRoomOccupied(room, when));
    }

    @Test
    void acceptsAFreeRoomAtTheRequestedMoment() {
        RepresentationRepository repository = mock(RepresentationRepository.class);
        RepresentationService service = new RepresentationService(repository);
        Room room = mock(Room.class);
        LocalDateTime when = LocalDateTime.of(2026, 9, 2, 11, 0);

        when(repository.existsByRoomAndWhen(room, when)).thenReturn(false);

        assertFalse(service.isRoomOccupied(room, when));
    }

}
