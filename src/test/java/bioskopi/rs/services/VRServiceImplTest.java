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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//import static bioskopi.rs.constants.TicketConstants.seat;
import static bioskopi.rs.constants.ViewingRoomConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class VRServiceImplTest {

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
    private ViewingRoomService viewingRoomService;

    @Before
    @Transactional
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


    }

    @Test
    public void getSeats()
    {
        seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("2", "6", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("3", "5", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("5", "4", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        //Adding new VR to show that method wont collect its seats

        viewingRoom = new ViewingRoom();
        viewingRoom.setName("Second VR");
        viewingRoom.setFacility(cinema);

        viewingRoomRepository.save(viewingRoom);

        seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);

        seat = new Seat("2", "8", SegmentEnum.NORMAL, viewingRoom);
        seatRepository.save(seat);
        ///////////////////////////////////////////////////////////////////////////

        List<Seat> seats = viewingRoomService.getSeatsById(DB_VR_ID);

        assertThat(seats).hasSize(4);

    }
}
