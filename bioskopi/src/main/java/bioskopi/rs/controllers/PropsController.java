package bioskopi.rs.controllers;

import bioskopi.rs.domain.Props;
import bioskopi.rs.services.PropsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public List<Props> getAll() {
        logger.info("Fetching all props");
        return propsService.findAllProps();
    }

    /**
     * @param description of targeted props
     * @return props that cointain given description
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{description}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Props getByDescription(@PathVariable String description) {
        logger.info("Fetching props with description {}", description);
        return propsService.findByDescription(description);
    }

}

