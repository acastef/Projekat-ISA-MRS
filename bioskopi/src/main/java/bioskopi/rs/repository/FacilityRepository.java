package bioskopi.rs.repository;

import bioskopi.rs.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicate with database for facility data
 */
public interface FacilityRepository extends JpaRepository<Facility,Long> {


}
