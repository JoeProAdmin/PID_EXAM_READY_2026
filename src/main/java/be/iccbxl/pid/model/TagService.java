package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getAll() {
        List<Tag> tags = new ArrayList<>();
        repository.findAll().forEach(tags::add);
        return tags;
    }

    /**
     * Retourne le mot-clé existant ou le crée après validation.
     */
    public Tag findOrCreate(String value) {
        String cleanValue = value == null ? "" : value.trim();

        if (cleanValue.isEmpty() || cleanValue.length() > 30) {
            throw new IllegalArgumentException(
                    "Le mot-clé est obligatoire et ne peut pas dépasser 30 caractères.");
        }

        Tag existing = repository.findByTagIgnoreCase(cleanValue);
        return existing != null ? existing : repository.save(new Tag(cleanValue));
    }
}
