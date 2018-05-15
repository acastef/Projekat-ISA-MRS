package bioskopi.rs.controllers;


import bioskopi.rs.domain.Ad;
import bioskopi.rs.domain.util.UploadResponse;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.AdService;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 *  Communicates with ads REST calls from frontend
 */

@RestController
@RequestMapping("/ads")
public class AdController {

    public static final Logger logger = LoggerFactory.getLogger(AdController.class);

    private final String IMAGE_PATH =   Paths.get("img", "ads").toString()
            + File.separator;

    @Autowired
    private AdService adService;

    @RequestMapping(method = RequestMethod.GET, value = "/all/active", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ad>>  getAllActive(){
        return new ResponseEntity<>(adService.getAllActive(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all/wait", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ad>>  getAllWait(){
        return new ResponseEntity<>(adService.getAllWait(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> add(@RequestBody Ad ad){
        ad.setDeadline(ad.getDeadline().minusMinutes(20L));
        try {
            return new ResponseEntity<>(adService.add(ad),HttpStatus.CREATED);
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
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return new ResponseEntity<>("Please select file to upload", HttpStatus.NO_CONTENT);
        }
        if (!ImageValidator.checkExtension(image.getOriginalFilename())) {
            return new ResponseEntity<>("Wrong image format. Acceptable formats are: GIF, JPG, PNG, BMP, TIFF",
                    HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] bytes = image.getBytes();
            UploadResponse result = ImageValidator.generateAdName(image.getOriginalFilename());
            Path path = Paths.get(result.getPath());
            Files.write(path, bytes);
            return new ResponseEntity<>(IMAGE_PATH + result.getName(), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Action failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param ad that needs to be updated
     * @return updated ad in database
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> changeAd(@RequestBody Ad ad) {
        String[] tokens = ad.getImage().split("/");
        ad.setImage(tokens[tokens.length - 1]);
        return new ResponseEntity<>(adService.add(ad), HttpStatus.CREATED);
    }

    /**
     * @param ad that needs to be accepted
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> acceptAd(@RequestBody Ad ad) {
        String[] tokens = ad.getImage().split("/");
        ad.setImage(tokens[tokens.length - 1]);
        adService.accept(ad);
        return new ResponseEntity<>("Successfully accept ad", HttpStatus.OK);
    }

    /**
     * @param ad that needs to be rejected
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> rejectAd(@RequestBody Ad ad) {
        String[] tokens = ad.getImage().split("/");
        ad.setImage(tokens[tokens.length - 1]);
        adService.reject(ad);
        return new ResponseEntity<>("Successfully reject ad", HttpStatus.OK);
    }

    /**
     * @param ad that needs to be rejected
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deleteAd(@RequestBody Ad ad) {
        adService.delete(ad);
        return new ResponseEntity<>("Successfully reject ad", HttpStatus.OK);
    }

}
