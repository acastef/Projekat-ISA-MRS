package bioskopi.rs.repository;

import bioskopi.rs.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface that communicate with database for user category data
 */
public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {

    @Query(value = "SELECT id FROM points_scale WHERE facility_id = ?1", nativeQuery = true)
    public long selectPointScaleId(long id);

    @Query(value = "SELECT * FROM user_category WHERE points_scale_id = ?1", nativeQuery = true)
    public List<UserCategory> categories(long id);

}
