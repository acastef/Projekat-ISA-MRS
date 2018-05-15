package bioskopi.rs.repository;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.ViewingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Interface that communicates with database for viewing rooms data
 */
public interface ViewingRoomRepository extends JpaRepository<ViewingRoom, Long> {

    @Query(value = "SELECT seats FROM ViewingRoom v WHERE v.id = ?1")
    public List<Seat> getSeats(Long id);

    @Query(value = "SELECT vr FROM ViewingRoom vr WHERE vr.facility.id = ?1")
    public List<ViewingRoom> getAllForFac(Long facId);

    @Query(value = "SELECT facility FROM ViewingRoom v WHERE v.id = ?1")
    public Facility getFacility(Long id);
}
