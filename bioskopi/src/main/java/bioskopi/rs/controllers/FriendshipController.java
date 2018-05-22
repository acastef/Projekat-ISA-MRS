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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(method = RequestMethod.GET, value = "/getAll/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<UserDTO>> getAll(@PathVariable String id){
        return new ResponseEntity<List<UserDTO>>(friendshipService.getAll(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method =RequestMethod.GET, value = "getAllNonFriends/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<UserDTO>> getAllNonFriends(@PathVariable String id){
        return new ResponseEntity<List<UserDTO>>(friendshipService.getAllNonFriends(Long.parseLong(id)), HttpStatus.OK);
    }

//    @RequestMapping(method=RequestMethod.POST, value = "addFriend/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public void
}
