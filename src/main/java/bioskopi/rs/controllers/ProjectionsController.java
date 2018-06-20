package bioskopi.rs.controllers;

import bioskopi.rs.domain.DTO.ProjectionDTO;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.TicketRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(ProjectionsController.class);

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private TicketRepository ticketRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Projection> getById(@PathVariable String id) {
        logger.info("Fetching one projection with id: {}", id);
        return new ResponseEntity<>(projectionService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Projection>> getAll(){
        return new ResponseEntity<List<Projection>>(projectionService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByTicket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Projection> getByTicketId(@PathVariable String id){
        Long idP = ticketRepository.getProjectionId(Long.parseLong(id));
        return new ResponseEntity<Projection>(projectionService.findById(idP), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Projection> save(@RequestBody Projection projection) throws ValidationException {
        logger.info("Saving projection scale with id {}", projection.getId());

        Projection temp = projectionService.findById(new Long(projection.getId()));

        //temp.setPrice(projection.getPrice());
        return new ResponseEntity<>( projectionService.save(projection), HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSeatsStatuses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<Long, Boolean>> seatsForProjection(@PathVariable String id) {

        List<Ticket> tickets = projectionService.getTickets(Long.parseLong(id));
        Projection projection = projectionService.findById(new Long(Long.parseLong(id)));

        return new ResponseEntity<>(projectionService.getSeatsStatuses(projection.getViewingRoom().getId(), tickets), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable String id) {

        return new ResponseEntity<>(projectionService.getTickets(Long.parseLong(id) ), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addProjectection(@RequestBody Projection p){

        //logger.info(p);
        try{
            Projection temp = projectionService.add(p);
            return new ResponseEntity<>(temp, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
       // return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteProjection(@PathVariable String id) {

        String message = projectionService.delete(Long.parseLong(id));

        if (message.equals("Great Success!"))
            return new ResponseEntity<>(message, HttpStatus.OK);
        else
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Projection> > getByUserId(@PathVariable String id) {
        logger.info("Fetching one projection with id: {}", id);
        return new ResponseEntity<>(projectionService.findByUserId(Long.parseLong(id)), HttpStatus.OK);
    }


}
