package bioskopi.rs.controllers;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RequestMapping("/tickets")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(CinemasController.class);

    @Autowired
    private TicketService ticketService;

    @Transactional
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> addTicket(@RequestBody Ticket t) {
        return new ResponseEntity<Ticket>(ticketService.add(t), HttpStatus.OK);
    }


    @Transactional
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket t) {
        //Ticket tick = ticketService.add(t);
        return new ResponseEntity<Ticket>(ticketService.update(t), HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTicket(@PathVariable String id) {
        //Ticket tick = ticketService.add(t);
        //return new ResponseEntity<String>(ticketService.delete(Long.parseLong(id)), HttpStatus.OK);

        String message = ticketService.delete(Long.parseLong(id));

        if (message.equals("Great Success!"))
            return new ResponseEntity<>(message, HttpStatus.OK);
        else
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/putToFastReservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> putToFastReservation(@PathVariable String id) {

        Boolean success = ticketService.putToFastReservation(Long.parseLong(id));

        return new ResponseEntity<>(success, HttpStatus.OK);

    }



}
