package bioskopi.rs.controllers;

import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.ViewingRoom;
import bioskopi.rs.services.ViewingRoomService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.List;

@Repository
@RequestMapping("/viewingRooms")
public class ViewingRoomController {

    private static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    ViewingRoomService viewingRoomService;

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ViewingRoom> getById(@PathVariable String id) {
        logger.info("Fetching one viewing room with id: {}", id);
        return new ResponseEntity<>(viewingRoomService.getById(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getVRsForFacility/{facilityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ViewingRoom>> getVRsForFacility(@PathVariable String facilityId)
    {
        logger.info("Fetching all viewing rooms with facility id: {}", facilityId);
        return new ResponseEntity<>(viewingRoomService.getAllForFacility(Long.parseLong(facilityId)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSeatsById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Seat>> getSeatsById(@PathVariable String id) {
        logger.info("Fetching one projection with id: {}", id);
        return new ResponseEntity<>(viewingRoomService.getSeatsById(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ViewingRoom>> getAll()
    {
        return new ResponseEntity<>(viewingRoomService.getAll(),HttpStatus.OK) ;
    }
}
