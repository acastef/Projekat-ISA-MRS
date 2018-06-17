package bioskopi.rs.repository;

import bioskopi.rs.domain.Seat;
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

    @Query(value = "SELECT * FROM ticket t Where t.projection.date =" +
            " (Select projection.date FROM ticket Order By projection.date ASC)", nativeQuery = true)
    Ticket getEarliest();

    @Query(value = "SELECT * FROM ticket  where projection = ?1", nativeQuery = true)
    Ticket getByProjectionId(long projId);

    @Query(value = "SELECT * FROM ticket t where t.facility_id = ?1", nativeQuery = true)
    List<Ticket> getTicketsForFacility(long id);

    @Query(value = "SELECT t.seat_id FROM ticket t WHERE t.projection_id IN " +
            "(SELECT p.id FROM projection p WHERE p.viewing_room_id = ?1) ", nativeQuery = true)
    List<Long> getTakenSeats(long VrId);

}
