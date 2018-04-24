package bioskopi.rs.controllers;

import bioskopi.rs.domain.UserCategory;
import bioskopi.rs.services.UserCategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Communicates with user category REST calls
 */
@RestController
@RequestMapping("/user_category")
public class UserCategoryController {


    public static final Logger logger = LoggerFactory.getLogger(PropsController.class);

    @Autowired
    private UserCategoryServiceImpl userCategoryService;

    /**
     * @return all available user categories in database
     */
    @RequestMapping(method = RequestMethod.GET ,value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserCategory>> getAll(){
        logger.info("Fetching all user categories");
        return new ResponseEntity<List<UserCategory>> (userCategoryService.findAll(), HttpStatus.OK) ;
    }

    /**
     * @param name of user category
     * @return user category with given name
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserCategory> getByName(@PathVariable String name){
        logger.info("Fetching user category with name {}", name);
        return new ResponseEntity<UserCategory>(userCategoryService.findByName(name),HttpStatus.OK) ;
    }
}