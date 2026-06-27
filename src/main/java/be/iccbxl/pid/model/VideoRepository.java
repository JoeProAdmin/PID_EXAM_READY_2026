package be.iccbxl.pid.model;

import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {

    Video findById(long id);

}