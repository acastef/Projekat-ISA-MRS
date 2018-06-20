package bioskopi.rs.repository;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser,Long> {

    @Query(value = "SELECT * FROM user u WHERE u.username = :username", nativeQuery = true)
    RegisteredUser getByUserName(@Param("username") String username);

    @Query(value = "SELECT * FROM user u WHERE u.id = ?1", nativeQuery = true)
    RegisteredUser findUserById(long id);


}
