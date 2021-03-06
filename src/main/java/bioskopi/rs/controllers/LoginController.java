package bioskopi.rs.controllers;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.DTO.CaTAdminDTO;
import bioskopi.rs.domain.DTO.RegisteredUserDTO;
import bioskopi.rs.services.FacilitiesService;
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
import java.util.Set;


@Repository
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FacilitiesService facilitiesService;

    /***
     * @return all registered users
     */

    @RequestMapping(method = RequestMethod.GET, value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisteredUser>> getUsers(){
        logger.info("Fetching all registered users");
        List<RegisteredUser> newList = userService.findAllUsers();
        return new ResponseEntity<List<RegisteredUser>> (userService.findAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getReg/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> getReg(@PathVariable String id){
        try{
            return new ResponseEntity<RegisteredUser>(userService.getRegById(Long.parseLong(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<RegisteredUser>(new RegisteredUser(), HttpStatus.BAD_REQUEST);
        }
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
            //facilitiesService.getFacilityById()
            session.setAttribute("user", user);
            Object u = userService.findByUsername(username);
            return new ResponseEntity<Object>(u, HttpStatus.OK);
        }else if(user.getAuthorities().equals(AuthorityEnum.USER) && user.getPassword().equals(password)){
            session.setAttribute("user",user);
            return new ResponseEntity<>("Redirect",HttpStatus.OK);
        }
        else if(!user.isFirstLogin() && user.getPassword().equals(password)){
            session.setAttribute("user",user);
            return new ResponseEntity<>("Redirect",HttpStatus.TEMPORARY_REDIRECT);
        }
        return new ResponseEntity<Object>("Incorrect username or password, or user is not activated.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getLogged", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findLoggedUser(HttpSession session){
        try{
            User u = (User)session.getAttribute("user");
            if (u != null) {
                if(u.getAuthorities() == AuthorityEnum.CAT){
                    CaTAdmin admin = (CaTAdmin) u;
                    CaTAdminDTO dto = new CaTAdminDTO(admin.getId(),admin.getName(),admin.getSurname(),admin.getEmail(),
                            admin.getUsername(), admin.getAvatar(),admin.getTelephone(),admin.getAddress(),
                            admin.getFacility().getId(),admin.getAuthorities(),admin.getPassword(),admin.isFirstLogin());

                    return new ResponseEntity<>(dto,HttpStatus.OK);
                }else if(u.getAuthorities() == AuthorityEnum.USER){
                    RegisteredUser registeredUser = (RegisteredUser) u;
                    RegisteredUserDTO dto = new RegisteredUserDTO(registeredUser,registeredUser.getPassword());
                    return new ResponseEntity<>(dto,HttpStatus.OK);
                }

                return new ResponseEntity<>(u, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>(new User(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity<Object>("Successfully logged out", HttpStatus.OK);
    }

}
