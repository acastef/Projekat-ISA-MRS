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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static bioskopi.rs.constants.TicketConstants.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {

    private static final String URL_PREFIX = "/tickets";
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

        DB_FAC_ID = facilityRepository.save(cinema).getId();

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName("VRn");
        viewingRoom.setFacility(cinema);
        seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);

        viewingRoomRepository.save(viewingRoom);
        seatRepository.save(seat);


        user = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
                new HashSet<Ticket>(), new ArrayList<Friendship>());

        DB_USER_ID = userRepository.save(user).getId();

        p = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>());

        Projection newP = projectionRepository.save(p);
        DB_PROJ_ID = newP.getId();

        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);

        DB_TIC_ID = ticketRepository.save(t).getId();
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void putToFastReservation() throws Exception
    {
        String path = URL_PREFIX + "/putToFastReservation/" + DB_TIC_ID;
        mockMvc.perform(put(path))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @Transactional
    public void delete() throws Exception
    {
        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);
        t = ticketRepository.save(t);

        Ticket t2 = new Ticket(2, SeatStatus.FREE, false, user, seat, p, cinema);
        t2 = ticketRepository.save(t2);


        mockMvc.perform(put(URL_PREFIX + "/delete/" + t.getId()))
                .andExpect(status().isOk());

    }
}
