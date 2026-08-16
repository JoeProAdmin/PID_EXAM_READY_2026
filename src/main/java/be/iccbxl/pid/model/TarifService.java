package be.iccbxl.pid.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TarifService {

    private static final List<String> TYPES = Arrays.asList("promo", "senior", "kids");

    private final TarifRepository tarifRepository;
    private final ShowRepository showRepository;

    public TarifService(TarifRepository tarifRepository, ShowRepository showRepository) {
        this.tarifRepository = tarifRepository;
        this.showRepository = showRepository;
    }

    public List<String> getTypes() {
        return TYPES;
    }

    public Tarif create(Long showId, String type, Double prix) {
        Show show = showId == null ? null : showRepository.findById(showId).orElse(null);
        if (show == null) {
            throw new IllegalArgumentException("Le spectacle sélectionné n'existe pas.");
        }

        String cleanType = type == null ? "" : type.trim().toLowerCase();
        if (!TYPES.contains(cleanType)) {
            throw new IllegalArgumentException("Le type de tarif est invalide.");
        }
        if (prix == null || prix < 0) {
            throw new IllegalArgumentException("Le prix doit être positif ou nul.");
        }

        return tarifRepository.save(new Tarif(cleanType, prix, show));
    }

    public List<Tarif> getForShow(Show show) {
        return tarifRepository.findByShowOrderByPrixAsc(show);
    }
}
