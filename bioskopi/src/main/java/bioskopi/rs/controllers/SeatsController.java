package bioskopi.rs.controllers;


import bioskopi.rs.domain.SegmentEnum;
import bioskopi.rs.repository.SeatRepository;
import bioskopi.rs.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    SeatService seatService;

    @RequestMapping(method = RequestMethod.PUT, value = "/changeSegment/{segmentType}")
    @ResponseBody
    public ResponseEntity<Boolean> changeSegment(@RequestBody List<Long> listOfIds, @PathVariable String segmentType) {
        return new ResponseEntity<>(seatService.changeSegment(listOfIds, SegmentEnum.valueOf(segmentType)), HttpStatus.OK);
    }

}
