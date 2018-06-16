package bioskopi.rs.controllers;


import bioskopi.rs.domain.*;
import bioskopi.rs.domain.util.UploadResponse;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.services.AdService;
import bioskopi.rs.services.MailService;
import bioskopi.rs.services.UserService;
import bioskopi.rs.validators.AuthorityValidator;
import bioskopi.rs.validators.ImageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    /**
     * @return all active ads
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all/active", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ad>>  getAllActive(){
        return new ResponseEntity<>(adService.getAllActive(),HttpStatus.OK);
    }

    /**
     * @return all ads that wait to be accepted or rejected
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all/wait", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Ad>>  getAllWait(){
        return new ResponseEntity<>(adService.getAllWait(),HttpStatus.OK);
    }


    /**
     * @param id of targeted ad
     * @return ad with given id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Ad>  getById(@PathVariable String id){
        try{
            return new ResponseEntity<>(adService.findById(Long.parseLong(id)),HttpStatus.OK);
        } catch (NumberFormatException e){
            throw new ValidationException("Wrong id");
        }

    }


    /**
     * @param ad that needs to be added
     * @return result of action add action
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> add(@RequestBody Ad ad, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.USER);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            ad.setOwner(userService.getByUsername(user.getUsername()));
            return new ResponseEntity<>(adService.add(ad),HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
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
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.USER);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

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
    public ResponseEntity<Object> changeAd(@RequestBody Ad ad, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.USER);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            ad.setOwner(userService.getByUsername(user.getUsername()));
            String[] tokens = ad.getImage().split("/");
            ad.setImage(tokens[tokens.length - 1]);
            return new ResponseEntity<>(adService.add(ad), HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


    }

    /**
     * @param ad that needs to be accepted
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> acceptAd(@RequestBody Ad ad, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.FUN);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Ad temp = adService.findById(ad.getId());
            String[] tokens = ad.getImage().split("/");
            temp.setImage(tokens[tokens.length - 1]);
            //ad.setImage(tokens[tokens.length - 1]);
            adService.accept(temp);
            return new ResponseEntity<>("Successfully accept ad", HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


    }

    /**
     * @param ad that needs to be rejected
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> rejectAd(@RequestBody Ad ad, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.FUN);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Ad temp = adService.findById(ad.getId());
            String[] tokens = ad.getImage().split("/");
            temp.setImage(tokens[tokens.length - 1]);
            //ad.setImage(tokens[tokens.length - 1]);
            adService.accept(temp);
            return new ResponseEntity<>("Successfully reject ad", HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


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

    /**
     * @param bid that needs to be added
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.POST, value = "/bid", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object>  addBid(@RequestBody Bid bid, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.USER);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            bid.setUser(userService.getByUsername(user.getUsername()));
            Ad ad = adService.addBid(bid);
            ad.setImage(IMAGE_PATH + ad.getImage());
            return new ResponseEntity<>(ad, HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    /**
     * @param bid that needs to be accepted
     * @return result of action
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/bid/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object>  acceptBid(@RequestBody Bid bid,HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if(user == null){
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if(!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>(){{add(AuthorityEnum.USER);}})){
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Ad temp = adService.acceptOffer(bid,userService.getByUsername(user.getUsername()));
            Bid accepted = new Bid();
            for (Bid b :
                    temp.getBids()) {
                if(b.getState() == BidState.ACCEPT){
                    accepted = b;
                }
            }
            mailService.sendNotification("Report for ad: " + temp.getName(),
                    "Description:" + temp.getDescription() + "\n" +
                            "Accepted bid from: " + accepted.getUser().getUsername() + " for: " +
                            accepted.getOffer() + "$");
            return new ResponseEntity<>("Offer successfully accepted", HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }catch (ValidationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MailException e){
            logger.info("Error message:" + e.getMessage());
            return new ResponseEntity<>("Failed to send email notification",HttpStatus.NOT_FOUND);
        }

//        try {
//            Ad temp = adService.acceptOffer(bid);
//            Bid accepted = new Bid();
//            for (Bid b :
//                    temp.getBids()) {
//                if(b.getState() == BidState.ACCEPT){
//                    accepted = b;
//                }
//            }
//            mailService.sendNotification("Report for ad: " + temp.getName(),
//                    "Description:" + temp.getDescription() + "\n" +
//                             "Accepted bid from: " + accepted.getUser().getUsername() + " for: " +
//                               accepted.getOffer() + "$");
//            return new ResponseEntity<>("Offer successfully accepted", HttpStatus.OK);
//        } catch (ValidationException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (MailException e){
//            logger.info("Error message:" + e.getMessage());
//            return new ResponseEntity<>("Failed to send email notification",HttpStatus.NOT_FOUND);
//        }
    }



}