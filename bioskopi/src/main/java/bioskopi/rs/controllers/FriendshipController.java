package bioskopi.rs.controllers;


import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import bioskopi.rs.services.FriendshipService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

//    @RequestMapping(method = RequestMethod.GET, value = "/getAll/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> getAllFriends(@PathVariable String id) throws JsonProcessingException {
//        List<Long> ids = friendshipService.getFriends(Long.parseLong(id));
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        String json = mapper.writeValueAsString(ids);
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }


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

    @RequestMapping(method=RequestMethod.POST, value = "/addFriend", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> addFriend(@RequestBody  List<UserDTO> users){
        friendshipService.addFriendship(users.get(0), users.get(1));
        return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.PUT, value = "/deleteFriend", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> deleteFriend(@RequestBody  List<UserDTO> users){
        friendshipService.deleteFriendship(users.get(0), users.get(1));
        return new ResponseEntity<Boolean>(true, HttpStatus.NO_CONTENT);
    }
}
