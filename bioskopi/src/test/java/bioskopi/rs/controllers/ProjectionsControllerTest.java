package bioskopi.rs.controllers;


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
import java.util.*;

import static bioskopi.rs.constants.ProjectionsConstants.*;
import static bioskopi.rs.constants.PropsConstants.DB_FAC;
import static bioskopi.rs.constants.PropsConstants.DB_LOC;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        DB_FAC = new Cinema("name1", "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        cin2 = new Cinema("Arena", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        DB_FAC.getPointsScales().setFacility(DB_FAC);
        cin2.getPointsScales().setFacility(cin2);

        facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(DB_FAC);
            add(cin2);
        }});


        viewingRoom = new ViewingRoom();
        viewingRoom.setName("nameVR");
        viewingRoom.setFacility(cin2);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        Seat seat2 = new Seat("2", "2", SegmentEnum.NORMAL, viewingRoom);
        Seat seat3 = new Seat("3", "3", SegmentEnum.NORMAL, viewingRoom);

        seats = new HashSet<>();
        seats.add(seat);
        seats.add(seat2);
        seats.add(seat3);

        //Person person = new Person();

//        personRepository.saveAll(new ArrayList<Person>() {{
//            add(person);
//        }});
//
//
//        RegisteredUser user = new RegisteredUser("username1", "pwd1", "picture1", new HashSet<>(), person);
//        registeredUserRepository.saveAll(new ArrayList<RegisteredUser>(){{
//            add(user);
//        }});
//
//        Ticket t = new Ticket(SeatStatus.TAKEN, false, user, seat, p, cin2);
//        Ticket t2 = new Ticket(SeatStatus.TAKEN, false, user, seat2, p, cin2);
//        //Ticket t3 = new Ticket(SeatStatus.TAKEN, false, user, seat, p, cin2);

        viewingRoom.setSeats(seats);
        cin2.getViewingRooms().add(viewingRoom);

        viewingRoomRepository.saveAll(new ArrayList<ViewingRoom>() {{
            add(viewingRoom);
        }});

        LocalDate today = LocalDate.now();

        p = new Projection( DB_PROJ_ID, DB_PROJ_NAME, today, DB_PROJ_PRICE, new HashSet<String>(),
                "genre1", "director1", 2, "picture1", "description1",
                new ViewingRoom(), new HashSet<Ticket>(),  new HashSet<Feedback>() );

        p.setFacility(cin2);
        p.setViewingRoom(viewingRoom);

        List<Projection> temp = projectionRepository.saveAll(new ArrayList<Projection>() {{
            add(p);
        }});



        if (!temp.isEmpty()) {
            for (Projection proj :
                    temp) {
                if (proj.getName().compareTo(DB_PROJ_NAME) == 0) {
                    DB_PROJ_ID = proj.getId();
                }
            }
        }
    }

    @Test
    public void getById() throws Exception {
        String path = URL_PREFIX + "/getById/" + DB_PROJ_ID;
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_PROJ_ID))
                .andExpect(jsonPath("$.name").value(DB_PROJ_NAME));
    }

    @Test
    @Transactional
    public void save() throws Exception {

//        Projection p2 = new Projection( DB_PROJ_NAME, LocalDate.now(), DB_PROJ_PRICE, new HashSet<String>(),
//                "genre1", "director1", 2, "picture1", "description1",
//                new ViewingRoom(), new HashSet<Ticket>() );

        p = projectionRepository.getOne(DB_PROJ_ID);
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
        Projection p2 = new Projection( "name2", LocalDate.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                new ViewingRoom(), new HashSet<Ticket>(), new HashSet<Feedback>() );

        p2.setFacility(DB_FAC);

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

    @Test
    @Transactional
    public void getSeatsStatuses() throws Exception
    {

//        long idSeat1 = 0;
//        long idSeat2 = 0;
//        long idSeat3 = 0;
//
//        for (Seat s :
//                seats) {
//            if (s.getSeatRow() == "1" && s.getSeatColumn() == "1") {
//                idSeat1 = s.getId();
//            }
//            else if (s.getSeatRow() == "2" && s.getSeatColumn() == "2") {
//                idSeat2 = s.getId();
//            }
//            else if (s.getSeatRow() == "3" && s.getSeatColumn() == "3") {
//                idSeat3 = s.getId();
//            }
//        }
//
//        HashMap<Long, Boolean> statuses = new HashMap<Long, Boolean>();
//        statuses.put(idSeat1, true);
//        statuses.put(idSeat2, true);
//        statuses.put(idSeat3, false);
//
//
//
//
//        String json = TestUtil.json(p);
//        ResultActions result =  mockMvc.perform(RestDocumentationRequestBuilders.put(URL_PREFIX + "/getSeatsStatuses/" + DB_PROJ_ID)
//                .contentType(contentType).content(json))
//                .andExpect(status().isOk());
//
//        String sad = result.toString();
//        System.out.print(sad);
    }
}
