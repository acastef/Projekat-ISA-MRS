package bioskopi.rs.controllers;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.AdminsService;
import bioskopi.rs.services.RegisteredUserService;
import bioskopi.rs.services.RegisteredUserServiceImpl;
import bioskopi.rs.services.UserService;
import bioskopi.rs.validators.AuthorityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private AdminsService adminsService;

    /**
     * @param admin that needs to be saved to database
     * @return saved admin in database
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/fan_zone", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addFanZone(@RequestBody FanZoneAdmin admin, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{
                add(AuthorityEnum.USER);
                add(AuthorityEnum.FUN);
                add(AuthorityEnum.CAT);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        try {
            FanZoneAdmin temp = adminsService.addFanZoneAdmin(admin);
            session.setAttribute("user",temp);
            return new ResponseEntity<>(temp, HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param admin that needs to be saved to database
     * @return saved admin in database
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/ct", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addCTZone(@RequestBody CaTAdmin admin, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{
                add(AuthorityEnum.USER);
                add(AuthorityEnum.FUN);
                add(AuthorityEnum.CAT);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        try {
            admin.setFacility(adminsService.getByIdCaTAdmins(admin.getId()).getFacility());
            CaTAdmin temp = adminsService.addCaTAdmin(admin);
            session.setAttribute("user",temp);
            return new ResponseEntity<>(temp, HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param registeredUser that needs to be saved to database
     * @return saved registeredUser in database
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addUser(@RequestBody RegisteredUser registeredUser, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{
                add(AuthorityEnum.USER);
                add(AuthorityEnum.FUN);
                add(AuthorityEnum.CAT);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        try {

            User temp = userService.save(registeredUser);
            session.setAttribute("user",temp);
            return new ResponseEntity<>(temp, HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
