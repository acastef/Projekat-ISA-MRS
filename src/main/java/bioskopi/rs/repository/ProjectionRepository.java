package bioskopi.rs.repository;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.ViewingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

/**
 * Interface that communicates with database for projection data
 */
public interface ProjectionRepository extends JpaRepository<Projection, Long> {

    @Query(value = "SELECT s FROM Seat s WHERE s.viewingRoom.id = ?1")
    List<Seat> getSeats(Long id);

    @Query(value = "SELECT t FROM Ticket t WHERE t.projection.id = ?1")
    List<Ticket> getTickets(Long id);
}
