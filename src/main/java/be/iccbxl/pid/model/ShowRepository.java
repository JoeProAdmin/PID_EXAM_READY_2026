package be.iccbxl.pid.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ShowRepository extends CrudRepository<Show, Long> {

    Show findById(long id);

    Show findBySlug(String slug);

    Show findByTitle(String title);

    List<Show> findByLocation(Location location);

    List<Show> findDistinctByTags_TagContainingIgnoreCase(String tag);

    @Query("select s from Show s where not exists "
            + "(select t from s.tags t where lower(t.tag) = lower(:tag))")
    List<Show> findShowsWithoutTag(@Param("tag") String tag);
}
