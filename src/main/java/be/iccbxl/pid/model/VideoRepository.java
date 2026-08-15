package be.iccbxl.pid.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends CrudRepository<Video, Long> {

    boolean existsByVideoUrl(String videoUrl);

    @Query("SELECT DISTINCT video FROM Video video "
            + "JOIN video.show spectacle "
            + "JOIN spectacle.artistTypes artistType "
            + "WHERE LOWER(artistType.artist.lastname) = LOWER(:lastname)")
    List<Video> findDistinctByArtistLastname(@Param("lastname") String lastname);
}
