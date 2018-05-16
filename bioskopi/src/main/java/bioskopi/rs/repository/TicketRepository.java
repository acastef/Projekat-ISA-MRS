package bioskopi.rs.repository;

import bioskopi.rs.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query(value = "SELECT * FROM Ticket t WHERE t.facility_id = ?1 AND t.fast_reservation = true AND t.taken = false", nativeQuery = true)
    List<Ticket> getFastTickets(long id);

    @Modifying
    @Query(value = "UPDATE Ticket SET fast_reservation = true WHERE id = ?1")
    void changeTicket(long id);

    @Modifying
    @Query(value = "UPDATE Ticket SET taken = true WHERE id = ?1")
    void makeFastReservation(long id);
}
