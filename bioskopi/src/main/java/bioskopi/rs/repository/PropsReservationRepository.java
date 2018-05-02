package bioskopi.rs.repository;

import bioskopi.rs.domain.PropsReservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicates with database for props reservation data
 */
public interface PropsReservationRepository extends JpaRepository<PropsReservation,Long> {

}
