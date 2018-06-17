package bioskopi.rs.controllers;


import bioskopi.rs.domain.*;
import bioskopi.rs.repository.*;
import bioskopi.rs.services.FacilitiesService;
import bioskopi.rs.services.FeedbackService;
import bioskopi.rs.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static bioskopi.rs.constants.FeedbackConstants.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FeedbackControllerTest {

    private static final String URL_PREFIX = "/feedback";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private ViewingRoomRepository viewingRoomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;


    @Before
    public void setUp() throws Exception {

        cinema = new Cinema(1L,"cinema", "address", "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(), new HashSet<>());

        cinema.getPointsScales().setFacility(cinema);

        DB_FAC_ID = facilityRepository.save(cinema).getId();

        RegisteredUser u = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
                new HashSet<Ticket>(), new ArrayList<Friendship>());

        user = userRepository.save(u);

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName("VRn");
        viewingRoom.setFacility(cinema);
        viewingRoomRepository.save(viewingRoom);

        p = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>());

        DB_PROJ_ID = projectionRepository.save(p).getId();
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @Transactional
    public void add() throws Exception
    {
        Feedback feed = new Feedback(DB_FEED_ID, 4, "Desp", user, p, cinema);
        //Feedback feed = feedbackService.save(f);

        String json = TestUtil.json(feed);

        int index2 = json.lastIndexOf("}");
        //json = json.substring(0,index2) + ",\"facility\":" +  TestUtil.json(cinema) + "}";
        json = json.substring(0,index2) + ",\"facility_id\":" +  DB_FAC_ID + "}";

        int index3 = json.lastIndexOf("}");
        p.setId(DB_PROJ_ID);
        json = json.substring(0,index3) + ",\"projection_id\":" + DB_PROJ_ID + "}";


        mockMvc.perform(post(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isOk());
    }


}
