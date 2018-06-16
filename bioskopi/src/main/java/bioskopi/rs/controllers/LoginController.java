package bioskopi.rs.controllers;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import bioskopi.rs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;


@Repository
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /***
     * @return all registered users
     */

    @RequestMapping(method = RequestMethod.GET, value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisteredUser>> getUsers(){
        logger.info("Fetching all registered users");
        List<RegisteredUser> newList = userService.findAllUsers();
        return new ResponseEntity<List<RegisteredUser>> (userService.findAllUsers(), HttpStatus.OK);
    }

    /***
     *
     * @param username of user
     * @return user with given username
     */

    @RequestMapping(method = RequestMethod.GET, value = "/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findUserWithUsernameAndPassword(@PathVariable String username, @PathVariable String password, HttpSession session) {
        logger.info("Fetching user with username...");
        User user = userService.findByUsername(username);
        if (user.isFirstLogin() && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return new ResponseEntity<Object>(userService.findByUsername(username), HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Incorrect username or password, or user is not activated.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getLogged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findLoggedUser(HttpSession session){
        try{
            User u = (User)session.getAttribute("user");
            if (u != null) {
                return new ResponseEntity<User>((User) session.getAttribute("user"), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity<Object>("Successfully logged out", HttpStatus.OK);
    }

}
