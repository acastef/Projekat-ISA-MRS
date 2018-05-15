package bioskopi.rs.repository;

import bioskopi.rs.domain.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Interface that communicate with database for ad data
 */
public interface AdRepository extends JpaRepository<Ad,Long> {

    @Query(value = "SELECT a FROM Ad a WHERE a.state = 0")
    Optional<List<Ad>> findAllActive();

    @Query(value = "SELECT a FROM Ad a WHERE a.state = 2")
    Optional<List<Ad>> findAllWait();
}
