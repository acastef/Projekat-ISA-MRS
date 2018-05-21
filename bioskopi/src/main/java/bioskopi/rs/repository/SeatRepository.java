package bioskopi.rs.repository;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.SegmentEnum;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Modifying
    @Query(value = "UPDATE Seat SET segment = 3 WHERE viewingRoom.id = ?1 AND segment = ?2")
    void closeSegment(long vrId, SegmentEnum segmentType);

}
