package bioskopi.rs.controllers;


import bioskopi.rs.domain.*;
import bioskopi.rs.repository.*;
import bioskopi.rs.services.FacilitiesService;
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
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static bioskopi.rs.constants.TicketConstants.DB_TIC_ID;
import static bioskopi.rs.constants.ViewingRoomConstants.*;
import static bioskopi.rs.constants.ViewingRoomConstants.seat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ViewingRoomControllerTest {

    private static final String URL_PREFIX = "/viewingRooms";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private FacilitiesService facilitiesService;

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

        facilityRepository.save(cinema);

        viewingRoom = new ViewingRoom();
        viewingRoom.setName("VRn");
        viewingRoom.setFacility(cinema);


        ViewingRoom savedVR =viewingRoomRepository.save(viewingRoom);
        DB_VR_ID = savedVR.getId();

        seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("2", "6", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("3", "5", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("5", "4", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getSeatsById() throws Exception
    {
        mockMvc.perform(get(URL_PREFIX + "/getSeatsById/" + DB_VR_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(4)));
    }
}
