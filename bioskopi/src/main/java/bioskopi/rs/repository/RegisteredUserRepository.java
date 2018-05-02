package bioskopi.rs.repository;

import bioskopi.rs.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser,Long> {
}
