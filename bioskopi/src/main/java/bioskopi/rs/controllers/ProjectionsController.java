package bioskopi.rs.controllers;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.ProjectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Repository
@RequestMapping("/projections")
public class ProjectionsController {

    private static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    ProjectionService projectionService;

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Projection> getById(@PathVariable String id) {
        logger.info("Fetching one projection with id: {}", id);
        return new ResponseEntity<>(projectionService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Projection> save(@RequestBody Projection projection) throws ValidationException {
        logger.info("Saving projection scale with id {}", projection.getId());

        Projection temp = projectionService.findById(new Long( projection.getId()));

        temp.setPrice(projection.getPrice());

        return new ResponseEntity<>( projectionService.save(temp), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSeatsStatuses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<Integer, Boolean>> seatsForProjection(@PathVariable String id){

        Projection temp = projectionService.findById(new Long( id));

        return new ResponseEntity<>(projectionService.getSeatsStatuses(temp.getViewingRoom().getId()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Ticket> getTickets(@PathVariable String id){

        ResponseEntity<List<Ticket>> t = new ResponseEntity<>(projectionService.getTickets(Long.parseLong(id) ), HttpStatus.OK);
        List<Ticket> temp = projectionService.getTickets(Long.parseLong(id) );
        for (Ticket tq: temp) {
            logger.info(tq.toString());
        }

        return temp;
    }
}
