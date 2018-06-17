package bioskopi.rs.controllers;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.SegmentEnum;
import bioskopi.rs.domain.ViewingRoom;
import bioskopi.rs.services.SeatService;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
@RequestMapping("/viewingRooms")
public class ViewingRoomController {

    private static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    ViewingRoomService viewingRoomService;

    @Autowired
    SeatService seatService;

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
        logger.info("Fetching seats from viewing Room with id: {}", id);
        return new ResponseEntity<>(viewingRoomService.getSeatsById(Long.parseLong(id)), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/closeSegment/{id}/{segmentType}")
    @ResponseBody
    public ResponseEntity<Boolean> closeSegment(@PathVariable String id, @PathVariable String segmentType) {
        return new ResponseEntity<>(viewingRoomService.closeSegment(Long.parseLong(id), SegmentEnum.valueOf(segmentType)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ViewingRoom>> getAll()
    {
        return new ResponseEntity<>(viewingRoomService.getAll(),HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFacility/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public  ResponseEntity<Facility> getFacility(@PathVariable String id)
    {
        return new ResponseEntity<>(viewingRoomService.getFacility(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTakenSeats/{VRId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Long>> getTakenSeats(@PathVariable String VRId)
    {

        List<Long> temp = viewingRoomService.getTakenSeats(Long.parseLong(VRId));
        List<Long> takenSeats = new LinkedList<Long>();
        for (Number id: temp) {
            takenSeats.add(id.longValue());
        }
        
        return new ResponseEntity<>(takenSeats, HttpStatus.OK);
    }
}
