package bioskopi.rs.controllers;

import bioskopi.rs.domain.*;
import bioskopi.rs.repository.*;
import bioskopi.rs.services.FacilitiesService;
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
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static bioskopi.rs.constants.FacilitiesConstants.*;
import static bioskopi.rs.constants.FacilityConstants.DB_ID;
import static bioskopi.rs.constants.FacilityConstants.DB_UNKNOWN_ID;
import static bioskopi.rs.domain.Privilege.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FacilitiesControllerTest {

    private static final String URL_PREFIX = "/facilities";
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

//        DB_FAC_CIN = new Cinema(DB_FAC_NAME, DB_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
//                new PointsScale(), new HashSet<>(),  new HashSet<>());

        DB_FAC_CIN = new Facility(DB_FAC_NAME, DB_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());


        Theater theater = new Theater("FAC_FAC2", "FAC_ADDR2", "theater",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        DB_FAC_CIN.getPointsScales().setFacility(DB_FAC_CIN);
        theater.getPointsScales().setFacility(theater);


        List<Facility> temp = facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(DB_FAC_CIN);
            add(theater);
        }});
        if (!temp.isEmpty()) {
            for (Facility fac :
                    temp) {
                if (fac.getName().compareTo(DB_FAC_NAME) == 0) {
                    DB_FAC_ID = fac.getId();
                }
            }
        }

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName("VRn");
        viewingRoom.setFacility(DB_FAC_CIN);
        viewingRoomRepository.save(viewingRoom);

        Projection p = new Projection("name1", LocalDateTime.now(), 111, new HashSet<String>(),
                "genre1", "director1", 11, "picture1", "description1",
                viewingRoom, new HashSet<Ticket>(), DB_FAC_CIN, new HashSet<Feedback>());

        projectionRepository.save(p);

        Projection p2 = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), DB_FAC_CIN, new HashSet<Feedback>());

        projectionRepository.save(p2);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;


    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_COUNT)));
    }

    @Test
    public void getAllFacilities() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_COUNT)));
    }

    @Test
    public void getRepertoireById() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/getRepertoire/" + DB_FAC_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getRepertoireByUnknownId() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/getRepertoire/" + DB_UNKNOWN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_FAC_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_FAC_ID))
                .andExpect(jsonPath("$.name").value(DB_FAC_NAME))
                .andExpect(jsonPath("$.address").value(DB_FAC_ADR));
    }

    @Test
    @Transactional
    public void addCinema() throws Exception {
        Cinema cinema = new Cinema(NEW_FAC_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        cinema.getPointsScales().setFacility(cinema);
        cinema.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), cinema.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), cinema.getPointsScales()),
                new UserCategory(BRONZE, 30L, new BigDecimal("15.83"), cinema.getPointsScales()))));

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(cinema);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        HashSet<Seat> seats = new HashSet<>();
        seats.add(seat);
        viewingRoom.setSeats(seats);
        cinema.getViewingRooms().add(viewingRoom);

        String json = TestUtil.json(cinema);
        mockMvc.perform(post(URL_PREFIX + "/addCinema")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isCreated());
    }

    /**
     * Adding facility with name that already exists in database
     */
    @Test
    @Transactional
    public void notUniqueNameAddCinema() throws Exception {
        Cinema cinema = new Cinema(DB_FAC_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        cinema.getPointsScales().setFacility(cinema);
        cinema.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), cinema.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), cinema.getPointsScales()),
                new UserCategory(BRONZE, 30L, new BigDecimal("15.83"), cinema.getPointsScales()))));

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(cinema);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        HashSet<Seat> seats = new HashSet<>();
        seats.add(seat);
        viewingRoom.setSeats(seats);
        cinema.getViewingRooms().add(viewingRoom);

        String json = TestUtil.json(cinema);
        mockMvc.perform(post(URL_PREFIX + "/addCinema")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void addTheater() throws Exception {
        Theater theater = new Theater(NEW_THA_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        theater.getPointsScales().setFacility(theater);
        theater.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), theater.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), theater.getPointsScales()),
                new UserCategory(BRONZE, 30L, new BigDecimal("15.83"), theater.getPointsScales()))));

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(theater);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        HashSet<Seat> seats = new HashSet<>();
        seats.add(seat);
        viewingRoom.setSeats(seats);
        theater.getViewingRooms().add(viewingRoom);

        String json = TestUtil.json(theater);
        mockMvc.perform(post(URL_PREFIX + "/addCinema")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void notUniqueNameAddTheater() throws Exception {
        Theater theater = new Theater(DB_FAC_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        theater.getPointsScales().setFacility(theater);
        theater.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), theater.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), theater.getPointsScales()),
                new UserCategory(BRONZE, 30L, new BigDecimal("15.83"), theater.getPointsScales()))));

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(theater);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        HashSet<Seat> seats = new HashSet<>();
        seats.add(seat);
        viewingRoom.setSeats(seats);
        theater.getViewingRooms().add(viewingRoom);

        String json = TestUtil.json(theater);
        mockMvc.perform(post(URL_PREFIX + "/addCinema")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void save() throws Exception
    {
        Facility newFac = facilityRepository.getOne(DB_ID);

        newFac.setDescription("newDescription");
        String json = TestUtil.json(newFac);

        mockMvc.perform(put(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void getFastTickets() throws Exception
    {

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(DB_FAC_CIN);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);

        viewingRoomRepository.save(viewingRoom);
        seatRepository.save(seat);


        RegisteredUser user = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
                new HashSet<Ticket>(), new ArrayList<Friendship>());


        userRepository.save(user);


        Projection p = new Projection( "name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), DB_FAC_CIN, new HashSet<Feedback>() );

        projectionRepository.save(p);



        Set<Ticket> tickets = DB_FAC_CIN.getTickets();
        Ticket t = new Ticket(1L, SeatStatus.FREE, false, user, seat, p, DB_FAC_CIN);
        t.setFastReservation(true);
        tickets.add(t);
        ticketRepository.saveAll(tickets);

        mockMvc.perform(get(URL_PREFIX + "/getFastTickets/" + DB_FAC_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}