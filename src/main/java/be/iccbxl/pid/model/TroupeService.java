package be.iccbxl.pid.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TroupeService {

    private final TroupeRepository repository;

    public TroupeService(TroupeRepository repository) {
        this.repository = repository;
    }

    public List<Troupe> getAll() {
        List<Troupe> troupes = new ArrayList<>();
        repository.findAll().forEach(troupes::add);
        return troupes;
    }

    public Troupe get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Troupe get(String id) {
        Long indice = Long.parseLong(id);
        return repository.findById(indice).orElse(null);
    }

    public Troupe add(Troupe troupe) {
        return repository.save(troupe);
    }

    public Troupe update(Troupe troupe) {
        return repository.save(troupe);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}