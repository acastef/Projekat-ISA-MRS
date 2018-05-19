package bioskopi.rs.controllers;


import bioskopi.rs.domain.Feedback;
import bioskopi.rs.services.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RequestMapping("/feedbacks")
public class FeedbackController {


    private static final Logger logger = LoggerFactory.getLogger(FacilitiesController.class);


    @Autowired
    private FeedbackService feedBackService;


    @RequestMapping(method = RequestMethod.POST, value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Feedback> save(@RequestBody Feedback f) {
        logger.info("Fetching all facilities");
        return new ResponseEntity<>(feedBackService.save(f), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByUserId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Feedback> >findByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(feedBackService.findByUserId(Long.parseLong(userId)), HttpStatus.OK);
    }
}
