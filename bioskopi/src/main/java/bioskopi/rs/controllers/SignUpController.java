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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Repository
@RequestMapping("/signup")
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserService userService;

    /***
     * @return message with action result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> add(@RequestBody RegisteredUser regUser){
        logger.info("Adding new registered user to database...");
        return new ResponseEntity<RegisteredUser>(userService.add(regUser), HttpStatus.CREATED);
    }
}
