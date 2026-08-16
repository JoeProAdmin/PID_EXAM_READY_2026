package be.iccbxl.pid.model;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByTagIgnoreCase(String tag);
}
