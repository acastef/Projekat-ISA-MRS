package bioskopi.rs.controllers;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.services.FacilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Repository
@RequestMapping("/cinemas")
public class CinemasController {

    private static final Logger logger = LoggerFactory.getLogger(CinemasController.class);

    @Autowired
    private FacilitiesService facilitiesService;

    /***
     *
     * @return all cinemas from DB
     */
    @RequestMapping(method = RequestMethod.GET, value = "/allCinemas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Facility>> findCinemas() {
        logger.info("Fetching all cinemas");
        List<Facility> newList =facilitiesService.findFacilityByType("cinema");
        return new ResponseEntity<List<Facility>>(facilitiesService.findFacilityByType("cinema"), HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Facility> save(@RequestBody Cinema cinema){
        logger.info("Saving facility with id {}", cinema.getId());
        return new ResponseEntity<>(facilitiesService.save(cinema), HttpStatus.CREATED);
    }
}
