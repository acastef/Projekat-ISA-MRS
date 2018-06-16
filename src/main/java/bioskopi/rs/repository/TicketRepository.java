package bioskopi.rs.repository;

import bioskopi.rs.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query(value = "SELECT * FROM ticket t WHERE t.facility_id = ?1 AND t.fast_reservation = true AND t.taken = false", nativeQuery = true)
    List<Ticket> getFastTickets(long id);

    @Modifying
    @Query(value = "UPDATE ticket SET fast_reservation = true WHERE id = ?1", nativeQuery = true)
    void changeTicket(long id);

    @Modifying
    @Query(value = "UPDATE Ticket SET taken = true WHERE id = ?1")
    void makeFastReservation(long id);

    @Query(value = "DELETE FROM ticket WHERE id = ?1", nativeQuery = true)
    void deleteReservation(long id);


    @Query(value = "SELECT * FROM ticket t where t.owner_id = ?1", nativeQuery = true)
    List<Ticket> getAllTickets(long id);
}
