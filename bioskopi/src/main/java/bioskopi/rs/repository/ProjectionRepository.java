package bioskopi.rs.repository;

import bioskopi.rs.domain.Projection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicates with database for projection data
 */
public interface ProjectionRepository extends JpaRepository<Projection, Long> {


}
