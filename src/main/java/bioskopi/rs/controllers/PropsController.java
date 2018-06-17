package bioskopi.rs.controllers;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.util.UploadResponse;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.TicketRepository;
import bioskopi.rs.services.PropsReservationService;
import bioskopi.rs.services.PropsService;
import bioskopi.rs.services.RegisteredUserServiceImpl;
import bioskopi.rs.services.UserService;
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
import javax.servlet.http.HttpSession;
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
import bioskopi.rs.validators.AuthorityValidator;

/**
 * Communicates with props REST calls from frontend
 */
@RestController
@RequestMapping("/props")
public class PropsController {

    public static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    private final String IMAGE_PATH = Paths.get("img", "props").toString()
            + File.separator;

    @Autowired
    private PropsService propsService;

    @Autowired
    private PropsReservationService propsReservationService;

    @Autowired
    private UserService userService;

    /**
     * @return collection of all available props in database
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PropsDTO>> getAll() {
        logger.info("Fetching all props");
        return new ResponseEntity<>(propsService.findAllProps(), HttpStatus.OK);
    }

    /**
     * @param description of targeted props
     * @return props that contain given description
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{description}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PropsDTO> getByDescription(@PathVariable String description) {
        logger.info("Fetching props with description {}", description);
        return new ResponseEntity<>(propsService.findByDescription(description), HttpStatus.OK);
    }

    /**
     * @param propsReservation that needs to be added to database
     * @return reservation of given props
     */
    @RequestMapping(method = RequestMethod.POST, value = "/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addReservation(@RequestBody PropsReservation propsReservation, HttpSession session) {
        logger.info("Adding reservation");
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if (!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>() {{
                add(AuthorityEnum.USER);
            }})) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            PropsDTO temp = propsService.findById(propsReservation.getProps().getId());
            propsReservation.setRegisteredUser(userService.getByUsername(user.getUsername()));
            PropsReservation props = propsReservationService.getByUserIdAndPropsId(propsReservation.getRegisteredUser().getId(),
                    propsReservation.getProps().getId());
            if (props.getId() == -1) {
                return new ResponseEntity<>(propsReservationService.add(propsReservation), HttpStatus.CREATED);
            }
            props.setQuantity(props.getQuantity() + propsReservation.getQuantity());
            return new ResponseEntity<>(propsReservationService.add(props), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Props do not exist", HttpStatus.BAD_REQUEST);
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
            if (user == null) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if (!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>() {{
                add(AuthorityEnum.FUN);
            }})) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } catch (ClassCastException e) {
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
            UploadResponse result = ImageValidator.generateName(image.getOriginalFilename());
            Path path = Paths.get(result.getPath());
            Files.write(path, bytes);
            return new ResponseEntity<>(IMAGE_PATH + result.getName(), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Action failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @param props that needs to be added
     * @return added props to database
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addProps(@RequestBody Props props, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if (!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>() {{
                add(AuthorityEnum.FUN);
            }})) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


        try {
            props.setActive(true);
            props.setReserved(false);
            Props temp = propsService.add(props);
            return new ResponseEntity<>(temp, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param props that needs to update
     * @return updated props
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> changeProps(@RequestBody Props props, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if (!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>() {{
                add(AuthorityEnum.FUN);
            }})) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }


        PropsDTO temp = propsService.findById(props.getId());
        if (temp.isReserved()) {
            return new ResponseEntity<>("Selected props can not be editable because is reserved by another user."
                    + "Please add new props with changed values", HttpStatus.BAD_REQUEST);
        }
        String[] tokens = props.getImage().split("/");
        props.setImage(tokens[tokens.length - 1]);
        return new ResponseEntity<>(propsService.add(props), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deleteProps(@RequestBody Props props, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if (!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>() {{
                add(AuthorityEnum.FUN);
            }})) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        try {
            String[] tokens = props.getImage().split("/");
            props.setImage(tokens[tokens.length - 1]);
            propsService.delete(props);
            return new ResponseEntity<>("Props successfully removed", HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Props does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/reserved", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getReservedProps(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }
            if (!AuthorityValidator.checkAuthorities(user, new ArrayList<AuthorityEnum>() {{
                add(AuthorityEnum.USER);
            }})) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(propsReservationService.getByUserId(user.getId()), HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } catch (ClassCastException e) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

    }


}

