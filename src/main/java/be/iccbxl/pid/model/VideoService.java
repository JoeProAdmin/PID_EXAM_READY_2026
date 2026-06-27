package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final VideoRepository repository;

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }

    public List<Video> getAll() {

        List<Video> videos = new ArrayList<>();

        repository.findAll().forEach(videos::add);

        return videos;
    }

    public Video get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Video get(String id) {
        Long indice = Long.parseLong(id);
        return repository.findById(indice).orElse(null);
    }

    public Video add(Video video) {
        return repository.save(video);
    }

    public Video update(Video video) {
        return repository.save(video);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}