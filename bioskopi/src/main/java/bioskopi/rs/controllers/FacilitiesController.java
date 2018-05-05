package bioskopi.rs.controllers;

import bioskopi.rs.domain.*;
import bioskopi.rs.services.FacilitiesService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Repository
@RequestMapping("/facilities")
public class FacilitiesController {

    private static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    private FacilitiesService facilitiesService;

    /**
     * @return collection of all available facilities in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Facility>> getAll() {
        logger.info("Fetching all facilities");
        return new ResponseEntity<>(facilitiesService.findAllFacilities(), HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRepertoire{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Projection>> getRepertoireById(@PathVariable String id) {
        logger.info("Fetching one repertoire with facility id: {}", id);
        return new ResponseEntity<>(facilitiesService.getRepertoireById(Long.parseLong(id)), HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Facility> getById(@PathVariable String id) {
        logger.info("Fetching one facility with id: {}", id);
        return new ResponseEntity<>(facilitiesService.getFacilityById(Long.parseLong(id)), HttpStatus.OK) ;
    }

    /**
     * @param facility that needs to be added to database
     * @return message of action result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addCinema", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addCinema(@Valid @RequestBody Cinema facility){
        logger.info("Inserting facility with name {}", facility.getName());
        try {
            return new ResponseEntity<>(facilitiesService.add(facility), HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("Facility name is already taken", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param facility that needs to be added to database
     * @return message of action result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addTheater", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addTheater(@Valid @RequestBody Theater facility){
        logger.info("Inserting facility with name {}", facility.getName());
        try {
            return new ResponseEntity<>(facilitiesService.add(facility), HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e){
            return new ResponseEntity<>("Facility name is already taken", HttpStatus.BAD_REQUEST);
        }
    }

}
