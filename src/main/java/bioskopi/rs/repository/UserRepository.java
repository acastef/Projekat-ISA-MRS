package bioskopi.rs.repository;

import bioskopi.rs.domain.FanZoneAdmin;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user u WHERE u.username = ?1", nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "SELECT * FROM user u WHERE u.id = ?1", nativeQuery = true)
    User findUserById(long id);


    @Query(value = "SELECT * FROM user u WHERE u.type = 'registered'", nativeQuery = true)
    List<User> findAll();

    @Modifying
    @Query(value = "UPDATE user SET first_login = 1 WHERE username = ?1", nativeQuery = true)
    void activateUser(String username);

    @Modifying
    @Query(value = "UPDATE user SET points = ?2 WHERE id = ?1", nativeQuery = true)
    void updatePoints(long id, int points);

}
