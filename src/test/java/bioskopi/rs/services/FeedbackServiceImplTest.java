package bioskopi.rs.services;


import bioskopi.rs.domain.*;
import bioskopi.rs.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static bioskopi.rs.constants.FeedbackConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FeedbackServiceImplTest {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;

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

    @Autowired
    private TicketService ticketService;

    @Before
    @Transactional
    public void setUp() throws Exception {


        cinema = new Cinema(1L,"cinema", "address", "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(), new HashSet<>());

        cinema.getPointsScales().setFacility(cinema);

        facilityRepository.save(cinema);


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

        projectionRepository.save(p);

    }

    @Test
    @Transactional
    public void add()
    {
        Feedback feed = new Feedback(DB_FEED_ID, 4, "Desp", user, p, cinema);

        feed = feedbackService.save(feed);

        Feedback newFeedback = feedbackRepository.getOne(feed.getId());
        assertThat(newFeedback).isNotNull();
        assertThat(newFeedback.getDescription()).isEqualTo(feed.getDescription());
        assertThat(newFeedback.getScore()).isEqualTo(feed.getScore());

    }
}
