package bioskopi.rs.controllers;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.util.UploadResponse;
import bioskopi.rs.domain.util.ValidationException;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import bioskopi.rs.validators.ImageValidator;

/**
 * Communicates wiht props REST calls from frontend
 */
@RestController
@RequestMapping("/props")
public class PropsController {

    public static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    private final String IMAGE_PATH =   Paths.get("img", "props").toString()
            + File.separator;

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

    /**
     * @param propsReservation that needs to be added to database
     * @return reservation of given props
     */
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

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image){
        if(image.isEmpty()){
            return new ResponseEntity<>("Please select file to upload", HttpStatus.NO_CONTENT);
        }
        if(!ImageValidator.checkExtension(image.getOriginalFilename())){
            return new ResponseEntity<>("Wrong image format. Acceptable formats are: GIF, JPG, PNG, BMP, TIFF",
                    HttpStatus.BAD_REQUEST);
        }

        try {
            byte [] bytes = image.getBytes();
            UploadResponse result = ImageValidator.generateName(image.getOriginalFilename());
            Path path = Paths.get(result.getPath());
            Files.write(path, bytes);
            return new ResponseEntity<>(IMAGE_PATH + result.getName(), HttpStatus.CREATED);
        }catch (IOException e){
            return new ResponseEntity<>("Action failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addProps(@RequestBody Props props){
        //AKO FEJLUJE OBRISI SLIKU
        try{
            Props temp = propsService.add(props);
            return new ResponseEntity<>(temp,HttpStatus.CREATED);
        }catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        //temp.setImage(IMAGE_PATH + temp.getImage());

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> changeProps(@RequestBody Props props){
        String[] tokens = props.getImage().split("/");
        props.setImage(tokens[tokens.length-1]);
        return new ResponseEntity<>(propsService.add(props),HttpStatus.CREATED);
    }

   /* @RequestMapping(method = RequestMethod.GET, value = "/upload")
    @ResponseBody
    public ResponseEntity<Object> uploadImage(){
        return new ResponseEntity<>("trigger upload", HttpStatus.CREATED);
    }*/

    @Autowired
    private RegisteredUserServiceImpl registeredUserService;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<RegisteredUser> user(@PathVariable String id){
        RegisteredUser temp = registeredUserService.findById(Long.parseLong(id));
        return new ResponseEntity<>(temp,HttpStatus.OK);
    }
}

