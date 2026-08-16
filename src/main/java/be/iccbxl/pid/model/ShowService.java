package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    @Autowired
    private ShowRepository repository;

    public List<Show> getAll() {
        List<Show> shows = new ArrayList<>();
        repository.findAll().forEach(shows::add);
        return shows;
    }

    public Show get(String id) {
        try {
            return get(Long.parseLong(id));
        } catch (RuntimeException exception) {
            return null;
        }
    }

    public Show get(Long id) {
        if (id == null) {
            return null;
        }
        Optional<Show> show = repository.findById(id);
        return show.orElse(null);
    }

    public void add(Show show) {
        repository.save(show);
    }

    public void update(String id, Show show) {
        repository.save(show);
    }

    public void delete(String id) {
        Long indice = Long.parseLong(id);
        repository.deleteById(indice);
    }

    public List<Show> getFromLocation(Location location) {
        return repository.findByLocation(location);
    }

    public List<Show> searchByTag(String value) {
        String cleanValue = value == null ? "" : value.trim();
        return cleanValue.isEmpty() ? getAll()
                : repository.findDistinctByTags_TagContainingIgnoreCase(cleanValue);
    }

    public List<Show> getWithoutTag(String value) {
        String cleanValue = value == null ? "" : value.trim();
        if (cleanValue.isEmpty()) {
            throw new IllegalArgumentException("Le mot-clé est obligatoire.");
        }
        return repository.findShowsWithoutTag(cleanValue);
    }

    public Show addTag(Long showId, Tag tag) {
        Show show = get(showId);
        if (show == null) {
            throw new IllegalArgumentException("Le spectacle sélectionné n'existe pas.");
        }
        if (show.getTags().contains(tag)) {
            throw new IllegalArgumentException("Ce mot-clé est déjà associé à ce spectacle.");
        }
        show.addTag(tag);
        return repository.save(show);
    }
}
