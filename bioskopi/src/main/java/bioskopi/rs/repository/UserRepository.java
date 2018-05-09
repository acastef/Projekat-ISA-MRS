package bioskopi.rs.repository;

import bioskopi.rs.domain.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<RegisteredUser, Long> {

    @Query(value = "SELECT * FROM registered_user ru WHERE ru.username = :username", nativeQuery = true)
    RegisteredUser findByUsername(@Param("username") String username);


}
