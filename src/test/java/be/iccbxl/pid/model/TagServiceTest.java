package be.iccbxl.pid.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class TagServiceTest {

    @Test
    void reusesAnExistingKeywordRegardlessOfCase() {
        TagRepository repository = mock(TagRepository.class);
        TagService service = new TagService(repository);
        Tag existing = new Tag("drame");
        when(repository.findByTagIgnoreCase("Drame")).thenReturn(existing);

        Tag result = service.findOrCreate("  Drame  ");

        assertSame(existing, result);
    }

    @Test
    void createsAValidNewKeyword() {
        TagRepository repository = mock(TagRepository.class);
        TagService service = new TagService(repository);
        when(repository.findByTagIgnoreCase("musique")).thenReturn(null);
        when(repository.save(any(Tag.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tag result = service.findOrCreate("musique");

        assertEquals("musique", result.getTag());
    }

    @Test
    void rejectsAnEmptyKeyword() {
        TagService service = new TagService(mock(TagRepository.class));

        assertThrows(IllegalArgumentException.class, () -> service.findOrCreate("  "));
    }
}
