package bioskopi.rs.controllers;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.DTO.FacilityDTO;
import bioskopi.rs.services.FacilitiesService;
import bioskopi.rs.validators.AuthorityValidator;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@RequestMapping("/facilities")
public class FacilitiesController {

    private static final Logger logger = LoggerFactory.getLogger(FacilitiesController.class);


    @Autowired
    private FacilitiesService facilitiesService;

    /**
     * @return collection of all available facilities in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<FacilityDTO>> getAll() {
        logger.info("Fetching all facilities");
        return new ResponseEntity<>(facilitiesService.findAllFacilities(), HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Facility>> getAllFacilites() {
        logger.info("Fetching all facilities");
        return new ResponseEntity<>(facilitiesService.getAll(), HttpStatus.OK) ;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRepertoire/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Projection>> getRepertoireById(@PathVariable String id) {
        logger.info("Fetching one repertoire with facility id: {}", id);
        return new ResponseEntity<>(facilitiesService.getRepertoireById(Long.parseLong(id)), HttpStatus.OK) ;
    }

    /**
     * @param id of target facility
     * @return facility with given id
     */
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
    public ResponseEntity<Object> addCinema(@RequestBody Cinema facility, HttpSession session){
        logger.info("Inserting facility with name {}", facility.getName());
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.SYS);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(facilitiesService.add(facility), HttpStatus.CREATED);
    }

    /**
     * @param facility that needs to be added to database
     * @return message of action result
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addTheater", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addTheater(@RequestBody Theater facility, HttpSession session) {
        logger.info("Inserting facility with name {}", facility.getName());
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.SYS);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(facilitiesService.add(facility), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Facility> save(@RequestBody Facility facility){
        logger.info("Saving facility with id {}", facility.getId());
        return new ResponseEntity<>(facilitiesService.save(facility), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFastTickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getFastTickets(@PathVariable String id)
    {
        logger.info("Getting fast tickets from facility with id: " + id);
        List<Ticket> temp =  facilitiesService.getFastTickets(Long.parseLong(id) );

        return new ResponseEntity<>(temp,  HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAverageScore/{facilityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<Long, Double>> getAverageScoreForProjections(@PathVariable String facilityId)
    {
        logger.info("Getting average score for projections from facility with id: " + facilityId);
        HashMap<Long, Double> temp =  facilitiesService.getProjectionsAverageScore(Long.parseLong(facilityId) );

        return new ResponseEntity<>(temp,  HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAverageFacilityScore/{facilityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Double> getAverageScore(@PathVariable String facilityId)
    {
        logger.info("Getting average score for  facility with id: " + facilityId);
        Double temp =  facilitiesService.getAverageScore(Long.parseLong(facilityId) );

        return new ResponseEntity<>(temp,  HttpStatus.OK);
    }



}