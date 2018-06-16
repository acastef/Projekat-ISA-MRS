package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;

import java.util.List;

public interface UserService{

    /***
     *
     * @return list of registereg users
     */

    List<RegisteredUser> findAllUsers();

    /***
     * @param username of user
     * @return User with given username
     */

    User findByUsername(String username);

    /***
     * @params New User
     * @return User added to Database
     */

    RegisteredUser add(RegisteredUser registeredUser);

    /***
     *
     * @param username of user who activated account
     */
    void activateUser(String username);

    /***
     * @param username of user
     * @return User with given username
     */

    RegisteredUser getByUsername(String username);

    /**
     * @param registeredUser that needs to be saved
     * @return saved user in database
     */
    User save(RegisteredUser registeredUser);
}
