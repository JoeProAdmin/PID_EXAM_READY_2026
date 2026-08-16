package be.iccbxl.pid.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TarifRepository extends CrudRepository<Tarif, Long> {

    List<Tarif> findByShowOrderByPrixAsc(Show show);
}
