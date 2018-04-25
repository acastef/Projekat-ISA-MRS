package bioskopi.rs.controllers;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Props;
import bioskopi.rs.services.FacilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Repository
@RequestMapping("/facilities")
public class FacilitiesController {

    public static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    public FacilitiesService facilitiesService;

    /**
     * @return collection of all available facilities in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Facility>> getAll() {
        logger.info("Fetching all facilities");
        return new ResponseEntity<List<Facility>>(facilitiesService.findAllFacilities(), HttpStatus.OK) ;
    }
}
