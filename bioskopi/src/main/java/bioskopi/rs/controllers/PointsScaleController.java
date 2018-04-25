package bioskopi.rs.controllers;

import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.UserCategory;
import bioskopi.rs.services.PointsScaleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Communicates with points scale REST calls
 */
@RestController
@RequestMapping("/points_scale")
public class PointsScaleController {

    private static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    private PointsScaleServiceImpl pointsScaleService;

    @RequestMapping(method = RequestMethod.GET ,value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PointsScale>> getAll(){
        logger.info("Fetching all points scale");
        return new ResponseEntity<List<PointsScale>> (pointsScaleService.getAll(), HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PointsScale> getByName(@PathVariable String id){
        logger.info("Fetching points scale with id {}", id);
        return new ResponseEntity<PointsScale>(pointsScaleService.getById(Long.parseLong(id)),HttpStatus.OK) ;
    }

}
