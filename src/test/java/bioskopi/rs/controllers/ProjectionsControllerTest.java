package bioskopi.rs.controllers;


import bioskopi.rs.constants.ProjectionsConstants;
import bioskopi.rs.domain.*;
import bioskopi.rs.repository.*;
import bioskopi.rs.util.TestUtil;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.apache.el.util.Validation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static bioskopi.rs.constants.ProjectionsConstants.*;
import static bioskopi.rs.constants.PropsConstants.DB_FAC;
import static bioskopi.rs.constants.PropsConstants.DB_LOC;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectionsControllerTest {

    private static final String URL_PREFIX = "/projections";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private ViewingRoomRepository viewingRoomRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private ProjectionRepository projectionRepository;

    @Before
    public void setUp() throws Exception {



        Cinema cin1 = new Cinema("loc1", "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        Cinema cin2 = new Cinema("PRESERVATION", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        PointsScale ps1 = new PointsScale();
        ps1.setFacility(cin1);

        PointsScale ps2 = new PointsScale();
        ps2.setFacility(cin2);

        cin1.setPointsScales(ps1);
        cin2.setPointsScales(ps2);

//        cin1.getViewingRooms().add(viewingRoom);
//        //cin2.getViewingRooms().add(viewingRoom);
//
//        viewingRoom.setFacility(cin1);


//        cin1.getPointsScales().setFacility(cin1);
//        cin2.getPointsScales().setFacility(cin2);

        Facility fac = facilityRepository.save(cin1);
        ProjectionsConstants.DB_FAC = fac;
        DB_FAC_ID = fac.getId();

        facilityRepository.save(cin2);



        //viewingRoomRepository.save(viewingRoom);



        Projection p = new Projection("name1", LocalDateTime.now(), 111, new HashSet<String>(),
                "genre1", "director1", 11, "picture1", "description1",
                viewingRoom, new HashSet<Ticket>(), ProjectionsConstants.DB_FAC, new HashSet<Feedback>());

        Projection pSaved = projectionRepository.save(p);
        DB_PROJ_ID = pSaved.getId();


    }

    @Test
    @Transactional
    public void save() throws Exception {

//        Projection p2 = new Projection( DB_PROJ_NAME, LocalDate.now(), DB_PROJ_PRICE, new HashSet<String>(),
//                "genre1", "director1", 2, "picture1", "description1",
//                new ViewingRoom(), new HashSet<Ticket>() );

        p = projectionRepository.findById(DB_PROJ_ID).get();
        p.setPrice(123);
        p.setName("newName");
        p.setGenre("Action");

//        p.setFacility(DB_FAC);
//
//        p.setViewingRoom(viewingRoom);

        String json = TestUtil.json(p);

        int index = json.lastIndexOf("}");
        json = json.substring(0,index) + ",\"facility\":{\"id\":" +  p.getFacility().getId() + "}}";

        this.mockMvc.perform(put(URL_PREFIX+ "/save").contentType(contentType).content(json))
                 .andDo(print())
                 .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    public void addProjection() throws Exception
    {
        Projection p2 = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), ProjectionsConstants.DB_FAC, new HashSet<Feedback>());

        Long idP2 = projectionRepository.save(p2).getId();

        String json = TestUtil.json(p2);
        int index = json.lastIndexOf("}");
        json = json.substring(0,index) + ",\"facility\":{\"id\":" +  p2.getFacility().getId() + "}}";


        this.mockMvc.perform(post(URL_PREFIX + "/add")
                .contentType(contentType).content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteProjection() throws Exception
    {

        String json = TestUtil.json(p);
        mockMvc.perform(RestDocumentationRequestBuilders.put(URL_PREFIX + "/delete/" + DB_PROJ_ID)
                .contentType(contentType).content(json))
                .andExpect(status().isOk());

    }


}
