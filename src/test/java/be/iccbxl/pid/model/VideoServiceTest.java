package be.iccbxl.pid.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class VideoServiceTest {

    @Test
    void detectsAnExistingVideoUrl() {
        VideoRepository repository = mock(VideoRepository.class);
        VideoService service = new VideoService(repository);
        String url = "https://youtu.be/ScMzIvxBSi4";

        when(repository.existsByVideoUrl(url)).thenReturn(true);

        assertTrue(service.videoUrlExists(url));
    }

    @Test
    void acceptsANewVideoUrl() {
        VideoRepository repository = mock(VideoRepository.class);
        VideoService service = new VideoService(repository);
        String url = "https://youtu.be/newVideo001";

        when(repository.existsByVideoUrl(url)).thenReturn(false);

        assertFalse(service.videoUrlExists(url));
    }
}
