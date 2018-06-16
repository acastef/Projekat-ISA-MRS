package bioskopi.rs.controllers;

import bioskopi.rs.domain.AuthorityEnum;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.MailService;
import bioskopi.rs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Repository
@RequestMapping("/signup")
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    /***
     * @return message with action result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody RegisteredUser regUser){
        try{
            regUser.setAuthorities(AuthorityEnum.USER);
            RegisteredUser registered = userService.add(regUser);
            if(registered != null){
                mailService.sendUserActivation(registered);
                logger.info("Adding new registered user to database...");
            }
            return new ResponseEntity<Object>("Successfully registered", HttpStatus.CREATED);
        }catch(ValidationException e) {
            return new ResponseEntity<Object>("Already exists!", HttpStatus.BAD_REQUEST);
        }catch(MailException me){
            return new ResponseEntity<Object>("Mail is not valid!", HttpStatus.BAD_REQUEST);
        }
    }

    /***
     *
     * @param username of user who activating account
     * @return message
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> activate(@PathVariable String username){
        userService.activateUser(username);
        return new ResponseEntity<Object>("Successfully activated", HttpStatus.OK);
    }
}
