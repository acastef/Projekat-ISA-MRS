package bioskopi.rs.controllers;


import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import bioskopi.rs.services.FriendshipService;
import bioskopi.rs.services.MailService;
import bioskopi.rs.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendshipController {

    private static final Logger logger = LoggerFactory.getLogger(FriendshipService.class);

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;



    @RequestMapping(method = RequestMethod.GET, value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<UserDTO>> getAll(HttpSession session) {

        try {
            RegisteredUser reg = (RegisteredUser) session.getAttribute("user");
            return new ResponseEntity<List<UserDTO>>(friendshipService.getAll(reg.getId()), HttpStatus.OK);
        }catch(NullPointerException e){
            return new ResponseEntity<List<UserDTO>>(new ArrayList<UserDTO>(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method =RequestMethod.GET, value = "/getAllNonFriends", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<UserDTO>> getAllNonFriends(HttpSession session){
        try {
            RegisteredUser registered = (RegisteredUser)session.getAttribute("user");
            return new ResponseEntity<List<UserDTO>>(friendshipService.getAllNonFriends(registered.getId()), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<List<UserDTO>>(new ArrayList<UserDTO>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value = "/addFriend/{id1}+{id2}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addFriend(@PathVariable String id1, @PathVariable String id2){
        try {
            User r1 = userService.getById(Long.parseLong(id1));
            User r2 = userService.getById(Long.parseLong(id2));
            UserDTO user1 = new UserDTO(r1.getId(), r1.getName(), r1.getSurname(), r1.getEmail(), r1.getUsername(), r1.getAvatar()
                                        , r1.getTelephone(), r1.getAddress());
            UserDTO user2 = new UserDTO(r2.getId(), r2.getName(), r2.getSurname(), r2.getEmail(), r2.getUsername(), r2.getAvatar()
                                        , r2.getTelephone(), r2.getAddress());
            friendshipService.addFriendship(user1, user2);
            return new ResponseEntity<Object>("Request accepted", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<Object>( "Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method=RequestMethod.POST, value = "/sendFriendRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> sendRequest(@RequestBody  List<UserDTO> users){
        try{
            mailService.sendFriendRequest(users);
            return new ResponseEntity<Object>("Request sent.", HttpStatus.OK);
        }catch (MailException e){
            return new ResponseEntity<Object>("Failed to send request", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method=RequestMethod.PUT, value = "/deleteFriend", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteFriend(@RequestBody  List<UserDTO> users){
        friendshipService.deleteFriendship(users.get(0), users.get(1));
        return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT);
    }
}
