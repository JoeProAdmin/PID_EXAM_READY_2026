package be.iccbxl.pid.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RepresentationRepository extends CrudRepository<Representation, Long> {

    List<Representation> findByShow(Show show);

    List<Representation> findByRoom(Room room);

    List<Representation> findByWhen(LocalDateTime when);

    List<Representation> findByRoomAndWhen(Room room, LocalDateTime when);

}