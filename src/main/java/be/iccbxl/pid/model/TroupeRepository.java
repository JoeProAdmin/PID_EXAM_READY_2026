package be.iccbxl.pid.model;

import org.springframework.data.repository.CrudRepository;

public interface TroupeRepository extends CrudRepository<Troupe, Long> {

    Troupe findById(long id);

    Troupe findByName(String name);
}