package bioskopi.rs.repository;

import bioskopi.rs.domain.PropsReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Interface that communicates with database for props reservation data
 */
public interface PropsReservationRepository extends JpaRepository<PropsReservation,Long> {

    @Query(value = "SELECT p FROM PropsReservation p WHERE p.registeredUser.id = :userId AND p.props.id = :propsId")
    Optional<PropsReservation> findByUserAndProps(@Param("userId") long userId,@Param("propsId") long propsId);

    @Query(value = "SELECT p FROM PropsReservation p WHERE p.registeredUser.id = :userId")
    Optional<List<PropsReservation>> findByUser(@Param("userId") long userId);
}