package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;

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

    RegisteredUser findByUsername(String username);

    /***
     * @params New User
     * @return User added to Database
     */

    RegisteredUser add(RegisteredUser registeredUser);


}
