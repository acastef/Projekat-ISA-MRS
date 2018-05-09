package bioskopi.rs.controllers;

import bioskopi.rs.domain.CaTAdmin;
import bioskopi.rs.domain.FanZoneAdmin;
import bioskopi.rs.domain.util.UploadResponse;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.AdminsService;
import bioskopi.rs.validators.ImageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Communicates with props REST calls from frontend
 */
@RestController
@RequestMapping("/admins")
public class AdminsController {

    public static final Logger logger = LoggerFactory.getLogger(AdminsController.class);

    private final String IMAGE_PATH =   Paths.get("img", "avatars").toString()
            + File.separator;

    @Autowired
    private AdminsService adminsService;

    /**
     * @return collection of all available fan-zone admins in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/fan_zone/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<FanZoneAdmin>> getAllFanZone() {
        logger.info("Fetching all fan-zone admins");
        return new ResponseEntity<>(adminsService.getAllFanZoneAdmins(), HttpStatus.OK) ;
    }

    /**
     * @param id of target fan-zone admin
     * @return fan-zone admin with given id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/fan_zone/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FanZoneAdmin> getByIdFanZone(@PathVariable String id) {
        logger.info("Fetching fan-zone admin with id {}", id);
        return new ResponseEntity<>(adminsService.getByIdFanZoneAdmin(Long.parseLong(id)), HttpStatus.OK) ;
    }

    /**
     * @param admin that needs to be saved to database
     * @return saved admin in database
     */
    @RequestMapping(method = RequestMethod.POST, value = "/fan_zone/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addFanZone(@RequestBody FanZoneAdmin admin){
        try{
            return new ResponseEntity<>(adminsService.addFanZoneAdmin(admin),HttpStatus.CREATED);
        }catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param image that needs to be uploaded
     * @return full image path
     */
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
            UploadResponse result = ImageValidator.generateAvatarName(image.getOriginalFilename());
            Path path = Paths.get(result.getPath());
            Files.write(path, bytes);
            return new ResponseEntity<>(IMAGE_PATH + result.getName(), HttpStatus.CREATED);
        }catch (IOException e){
            return new ResponseEntity<>("Action failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param admin that needs to be updated
     * @return updated admin
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/fan_zone/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> changeFanZone(@RequestBody FanZoneAdmin admin){
        String[] tokens = admin.getAvatar().split("/");
        admin.setAvatar(tokens[tokens.length-1]);
        return new ResponseEntity<>(adminsService.addFanZoneAdmin(admin),HttpStatus.CREATED);
    }

    /**
     * @return collection of all available cinema or theater admins in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/ct/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CaTAdmin>> getAllCT() {
        logger.info("Fetching all cinema or theater admins");
        return new ResponseEntity<>(adminsService.getAllCaTAdmins(), HttpStatus.OK) ;
    }

    /**
     * @param id of target cinema or theater admin
     * @return fan-zone admin with given id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/ct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CaTAdmin> getByIdCT(@PathVariable String id) {
        logger.info("Fetching cinema or theater admin with id {}", id);
        return new ResponseEntity<>(adminsService.getByIdCaTAdmins(Long.parseLong(id)), HttpStatus.OK) ;
    }

    /**
     * @param admin that needs to be saved to database
     * @return saved admin in database
     */
    @RequestMapping(method = RequestMethod.POST, value = "/ct/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addFanZone(@RequestBody CaTAdmin admin){
        try{
            return new ResponseEntity<>(adminsService.addCaTAdmin(admin),HttpStatus.CREATED);
        }catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param admin that needs to be updated
     * @return updated admin
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/ct/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> changeCT(@RequestBody CaTAdmin admin){
        String[] tokens = admin.getAvatar().split("/");
        admin.setAvatar(tokens[tokens.length-1]);
        return new ResponseEntity<>(adminsService.addCaTAdmin(admin),HttpStatus.CREATED);
    }

}
