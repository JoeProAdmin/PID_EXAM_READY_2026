package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypologyService {

    @Autowired
    private TypologyRepository repository;

    public List<Typology> getAll() {
        List<Typology> typologies = new ArrayList<>();
        repository.findAll().forEach(typologies::add);
        return typologies;
    }

    public Typology get(String id) {
        Long indice = Long.parseLong(id);
        Optional<Typology> typology = repository.findById(indice);
        return typology.orElse(null);
    }

    public Typology get(Long id) {
        Optional<Typology> typology = repository.findById(id);
        return typology.orElse(null);
    }

    public void add(Typology typology) {
        repository.save(typology);
    }

    public void update(Typology typology) {
        repository.save(typology);
    }
}