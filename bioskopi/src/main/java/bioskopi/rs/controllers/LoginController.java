package bioskopi.rs.controllers;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Repository
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(PropsController.class);

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
}
