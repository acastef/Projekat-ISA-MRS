package bioskopi.rs.repository;

import bioskopi.rs.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicate with database for user category data
 */
public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {
}
