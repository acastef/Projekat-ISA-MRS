package bioskopi.rs.repository;

import bioskopi.rs.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.registeredUser = ?1")
    List<Feedback> findByUserId(long id);
}
