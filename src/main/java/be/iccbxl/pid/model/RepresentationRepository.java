package be.iccbxl.pid.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RepresentationRepository extends CrudRepository<Representation, Long> {
	List<Representation> findByShow(Show show);
	List<Representation> findByLocation(Location location);
	List<Representation> findByWhen(LocalDateTime when);
}
