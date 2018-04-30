package bioskopi.rs.controllers;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Props;
import bioskopi.rs.services.PropsService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Communicates wiht props REST calls from frontend
 */
@RestController
@RequestMapping("/props")
public class PropsController {

    public static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    private PropsService propsService;

    /**
     * @return collection of all available props in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropsDTO>> getAll() {
        logger.info("Fetching all props");
        return new ResponseEntity<List<PropsDTO>>(propsService.findAllProps(), HttpStatus.OK) ;
    }

    /**
     * @param description of targeted props
     * @return props that cointain given description
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{description}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PropsDTO> getByDescription(@PathVariable String description) {
        logger.info("Fetching props with description {}", description);
        return new ResponseEntity<PropsDTO>(propsService.findByDescription(description), HttpStatus.OK);
    }



}

