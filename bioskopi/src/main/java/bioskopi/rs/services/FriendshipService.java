package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.Friendship;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import bioskopi.rs.repository.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


public interface FriendshipService{

    /***
     *
     * @param id of logged user
     * @return all his friends id-s
     */
    List<BigInteger> getFriends(long id);

    /***
     * @param id of logged user
     * @return all user's friends
     */
    List<UserDTO> getAll(long id);

    /***
     *
     * @param id registered user's ID
     * @return all user that are not registered user's friends
     */
    List<UserDTO> getAllNonFriends(long id);

    /***
     * @param first - registered user
     * @param second - new friend
     */
    void addFriendship(UserDTO first, UserDTO second);

    /***
     *
     * @param first registered user
     * @param second friend to remove from list
     */
    void deleteFriendship(UserDTO first, UserDTO second);


}
