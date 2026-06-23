package be.iccbxl.pid.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
	Location findByDesignation(String designation);
	Optional<Location> findById(Long id);
}
