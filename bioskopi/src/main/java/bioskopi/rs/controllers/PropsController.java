package bioskopi.rs.controllers;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.services.PropsReservationService;
import bioskopi.rs.services.PropsService;
import bioskopi.rs.services.RegisteredUserServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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

    @Autowired
    private PropsReservationService propsReservationService;

    /**
     * @return collection of all available props in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
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

    @RequestMapping(method = RequestMethod.POST, value = "/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addReservation(@RequestBody PropsReservation propsReservation) {
        logger.info("Adding reservation");

        try {
            PropsDTO temp = propsService.findById(propsReservation.getProps().getId());
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>("Props do not exist",HttpStatus.BAD_REQUEST);
        }
        PropsReservation props = propsReservationService.getByUserIdAndPropsId(propsReservation.getRegisteredUser().getId(),
                propsReservation.getProps().getId());
        if(props.getId() == -1){
            return new ResponseEntity<>(propsReservationService.add(propsReservation), HttpStatus.CREATED);
        }
        props.setQuantity(props.getQuantity() + propsReservation.getQuantity());
        return new ResponseEntity<>(propsReservationService.add(props), HttpStatus.CREATED);

    }

    @Autowired
    private RegisteredUserServiceImpl registeredUserService;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<RegisteredUser> user(@PathVariable String id){
        RegisteredUser temp = registeredUserService.findById(Long.parseLong(id));
        return new ResponseEntity<>(temp,HttpStatus.OK);
    }
}

