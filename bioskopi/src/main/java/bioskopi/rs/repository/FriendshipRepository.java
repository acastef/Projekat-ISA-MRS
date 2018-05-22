package bioskopi.rs.repository;

import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.Friendship;
import bioskopi.rs.domain.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query(value = "SELECT f.second_id FROM Friendship f WHERE f.first_id = ?1", nativeQuery = true)
    List<BigInteger> getAllFriends(long id);

    @Query(value = "SELECT u.id FROM User u WHERE u.id != ?1 and " +
            "u.id not in (SELECT f.second_id FROM Friendship f WHERE f.first_id = ?1)", nativeQuery = true)
    List<BigInteger> getAllNonFriends(long id);

    @Query(value = "SELECT * FROM Friendship f where f.first_id = ?1 and f.second_id = ?2", nativeQuery = true)
    Friendship getFriendshipByUsers(long id1, long id2);


}
