package bioskopi.rs.controllers;

import bioskopi.rs.domain.Facility;
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
@RequestMapping("/theaters")
public class TheatersController {

    private static final Logger logger = LoggerFactory.getLogger(TheatersController.class);

    @Autowired
    private FacilitiesService facilitiesService;

    /***
     *
     * @return all theaters from DB
     */
    @RequestMapping(method = RequestMethod.GET, value = "/allTheaters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Facility>> findTheaters() {
        logger.info("Fetching all theaters");
        List<Facility> newList =facilitiesService.findFacilityByType("theater");
        return new ResponseEntity<List<Facility>>(facilitiesService.findFacilityByType("theater"), HttpStatus.OK) ;
    }
}
