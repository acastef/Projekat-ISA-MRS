package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static bioskopi.rs.constants.TicketConstants.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketServiceImplTest {

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

    @Autowired
    private TicketService ticketService;

    @Before
    @Transactional
    public void setUp() throws Exception {


        cinema = new Cinema(1L,"cinema", "address", "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(), new HashSet<>());

        cinema.getPointsScales().setFacility(cinema);

        facilityRepository.save(cinema);

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName("VRn");
        viewingRoom.setFacility(cinema);
        seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);

        viewingRoomRepository.save(viewingRoom);
        seatRepository.save(seat);


        user = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
                new HashSet<Ticket>(), new ArrayList<Friendship>());


        userRepository.save(user);


        p = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>());

        projectionRepository.save(p);

    }

    @Test
    @Transactional
    public void putToFastReservation() throws Exception
    {
        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);

        t = ticketRepository.save(t);

        ticketService.putToFastReservation(t.getId());

        Ticket newTicket = ticketRepository.getOne(t.getId());
        assertThat(newTicket.isFastReservation()).isEqualTo(true);
    }

//    @Test
//    @Transactional
//    public void add()
//    {
//        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);
//        t = ticketService.add(t);
//
//        Ticket newTicket = ticketRepository.getOne(t.getId());
//        assertThat(newTicket).isNotNull();
//        assertThat(newTicket.getSeat()).isEqualTo(t.getSeat());
//        assertThat(newTicket.getProjection()).isEqualTo(t.getProjection());
//
//    }

    @Test
    @Transactional
    public void delete()
    {
        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);
        t = ticketRepository.save(t);

        Ticket t2 = new Ticket(2, SeatStatus.FREE, false, user, seat, p, cinema);
        t2 = ticketRepository.save(t2);

        ticketService.delete(t.getId());
        //gets all ticket from one user
        List<Ticket> ticketList = ticketRepository.getAllTickets(user.getId());

        assertThat(ticketList).isNotNull();
        assertThat(ticketList).hasSize(1);

    }

    @Transactional
    @Test
    public void update()
    {
        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);
        t = ticketRepository.save(t);

        int discount = 40;
        t.setDiscount(discount);

        ticketService.update(t);

        Ticket savedTicket = ticketRepository.getOne(t.getId());

        assertThat(savedTicket.getDiscount()).isEqualTo(discount);
        assertThat(savedTicket.getProjection()).isEqualTo(t.getProjection());
        assertThat(savedTicket.getOwner()).isEqualTo(t.getOwner());


    }

    @Transactional
    @Test(expected = ValidationException.class)
    public void updateWrongDiscount()
    {
        Ticket t = new Ticket(DB_TIC_ID, SeatStatus.FREE, false, user, seat, p, cinema);
        t = ticketRepository.save(t);

        int discount = 120;
        t.setDiscount(discount);

        //DB_INIT = false;

        ticketService.update(t);

        Ticket savedTicket = ticketRepository.getOne(t.getId());

        assertThat(savedTicket.getDiscount()).isEqualTo(discount);
        assertThat(savedTicket.getProjection()).isEqualTo(t.getProjection());
        assertThat(savedTicket.getOwner()).isEqualTo(t.getOwner());


    }

}
