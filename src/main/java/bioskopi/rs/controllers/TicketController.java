package bioskopi.rs.controllers;

import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.MailService;
import bioskopi.rs.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MailService mailService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket t) {
        return new ResponseEntity<Ticket>(ticketService.add(t), HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket t) {
        //Ticket tick = ticketService.add(t);
        return new ResponseEntity<Ticket>(ticketService.update(t), HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteTicket(@PathVariable String id) {
        ticketService.deleteReservation(Long.parseLong(id));
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/putToFastReservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putToFastReservation(@PathVariable String id) {

        try {
            Boolean success = ticketService.putToFastReservation(Long.parseLong(id));
            return new ResponseEntity<>(success, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/makeFastReservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> makeFastReservation(@PathVariable String id)
    {
        try{
            return new ResponseEntity<>(ticketService.makeFastReservation(Long.parseLong(id)),HttpStatus.CREATED);
        }
        catch (javax.validation.ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable String id){
        return new ResponseEntity<List<Ticket>>(ticketService.getTickets(Long.parseLong(id)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getVisitsByWeeks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<String, Integer>> getVisitsByWeeks(@PathVariable String id)
    {
        return new ResponseEntity<HashMap<String, Integer>>(ticketService.getVisitsByWeeks(Long.parseLong(id) ), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getVisitsByMonths/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<String, Integer>> getVisitsByMonths(@PathVariable String id)
    {
        return new ResponseEntity<HashMap<String, Integer>>(ticketService.getVisitsByMonths(Long.parseLong(id) ), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getPriceForFacility/{id}/{d1}/{d2}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> getPriceForFacility(@PathVariable String id, @PathVariable String d1,
                                                       @PathVariable String d2)
    {
        return new ResponseEntity<Integer>(ticketService.getPricePerPeriod(Long.parseLong(id), LocalDateTime.parse(d1),
                LocalDateTime.parse(d2) ), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/sendInvitation/{projId}+{seatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendInvitation(@RequestBody UserDTO user, @PathVariable String projId, @PathVariable String seatId){
        try{

            mailService.sendInvitation(user, projId, seatId);
            return new ResponseEntity<Object>("Successfully invited friend", HttpStatus.OK);
        }catch (MailException e){
            return new ResponseEntity<Object>("Failed to send invitation to friend", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/invitation/{userId}+{projId}+{seatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> acceptInvitation(@PathVariable String userId, @PathVariable String projId,
                                                    @PathVariable String seatId){
        return new ResponseEntity<Object>(ticketService.changeOwner(Long.parseLong(userId),
                Long.parseLong(projId),Long.parseLong(seatId)), HttpStatus.OK);

    }

}
