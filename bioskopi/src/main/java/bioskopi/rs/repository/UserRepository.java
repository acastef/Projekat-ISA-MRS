package bioskopi.rs.repository;

import bioskopi.rs.domain.FanZoneAdmin;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user u WHERE u.username = :username", nativeQuery = true)
    RegisteredUser findByUsername(@Param("username") String username);

}
