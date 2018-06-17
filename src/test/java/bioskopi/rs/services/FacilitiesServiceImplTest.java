package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.DTO.FacilityDTO;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static bioskopi.rs.constants.FacilitiesConstants.*;
import static bioskopi.rs.constants.FacilityConstants.DB_UNKNOWN_ID;
import static bioskopi.rs.domain.Privilege.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FacilitiesServiceImplTest {

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
    @Transactional
    public void setUp() throws Exception {


        Cinema cinema = new Cinema(DB_FAC_NAME, DB_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());

        Theater theater = new Theater("FAC_FAC2", "FAC_ADDR2", "theater",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        cinema.getPointsScales().setFacility(cinema);
        theater.getPointsScales().setFacility(theater);


//        Projection projection1 = new Projection("Proj1");
//        Projection projection2 = new Projection("Proj2");
//        Projection projection3 = new Projection("Proj3");

        List<Facility> temp = facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(cinema);
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
        viewingRoom.setFacility(cinema);
        viewingRoomRepository.save(viewingRoom);

        Projection p = new Projection("name1", LocalDateTime.now(), 111, new HashSet<String>(),
                "genre1", "director1", 11, "picture1", "description1",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>());

        projectionRepository.save(p);

        Projection p2 = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>());

        projectionRepository.save(p2);

    }

    @Test
    public void findAllFacilities() {
        List<FacilityDTO> allFacilities = facilitiesService.findAllFacilities();
        assertThat(allFacilities).hasSize(DB_COUNT);
    }

    @Test
    public void getAll(){
        List<Facility> allFacilities = facilitiesService.getAll();
        assertThat(allFacilities).hasSize(DB_COUNT);
    }

    @Test
    public void getFacilityById() {
        Facility facility = facilitiesService.getFacilityById(DB_FAC_ID);
        assertThat(facility).isNotNull();
        assertThat(facility.getId()).isEqualTo(DB_FAC_ID);
        assertThat(facility.getName()).isEqualTo(DB_FAC_NAME);
        assertThat(facility.getAddress()).isEqualTo(DB_FAC_ADR);
    }

    @Test
    @Transactional
    public void add() {
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

        int count = facilitiesService.findAllFacilities().size();

        Facility dbFacility = facilitiesService.add(cinema);
        assertThat(dbFacility).isNotNull();

        List<FacilityDTO> facilities = facilitiesService.findAllFacilities();
        assertThat(facilities).hasSize(count + 1);

        /*dbFacility = facilities.get(facilities.size()-1);
        assertThat(dbFacility.getName()).isEqualTo(NEW_FAC_NAME);
        assertThat(dbFacility.getAddress()).isEqualTo(NEW_FAC_ADR);*/


    }

    /**
     * Adding facility with name that already exists in database
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void notUniqueNameAdd() {
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

        int count = facilitiesService.findAllFacilities().size();

        DB_INIT = false;

        Facility dbFacility = facilitiesService.add(cinema);
        assertThat(dbFacility).isNotNull();

        List<FacilityDTO> facilities = facilitiesService.findAllFacilities();
        assertThat(facilities).hasSize(count + 1);

        /*dbFacility = facilities.get(facilities.size()-1);
        assertThat(dbFacility.getName()).isEqualTo(NEW_FAC_NAME);
        assertThat(dbFacility.getAddress()).isEqualTo(NEW_FAC_ADR);*/
    }


    /**
     * Facility does not contain any viewing room
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void noViewingRoomsAdd() {
        Cinema cinema = new Cinema(NEW_FAC_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        cinema.getPointsScales().setFacility(cinema);
        cinema.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), cinema.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), cinema.getPointsScales()),
                new UserCategory(BRONZE, 30L, new BigDecimal("15.83"), cinema.getPointsScales()))));


        int count = facilitiesService.findAllFacilities().size();

        DB_INIT = false;

        Facility dbFacility = facilitiesService.add(cinema);
        assertThat(dbFacility).isNotNull();

        List<FacilityDTO> facilities = facilitiesService.findAllFacilities();
        assertThat(facilities).hasSize(count + 1);

        /*dbFacility = facilities.get(facilities.size()-1);
        assertThat(dbFacility.getName()).isEqualTo(NEW_FAC_NAME);
        assertThat(dbFacility.getAddress()).isEqualTo(NEW_FAC_ADR);*/
    }

    /**
     * Facility does not contain points scale
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void noPointsScaleAdd() {
        Cinema cinema = new Cinema(NEW_FAC_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        cinema.getPointsScales().setFacility(cinema);

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(cinema);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        HashSet<Seat> seats = new HashSet<>();
        seats.add(seat);
        viewingRoom.setSeats(seats);
        cinema.getViewingRooms().add(viewingRoom);

        int count = facilitiesService.findAllFacilities().size();

        DB_INIT = false;

        Facility dbFacility = facilitiesService.add(cinema);
        assertThat(dbFacility).isNotNull();

        List<FacilityDTO> facilities = facilitiesService.findAllFacilities();
        assertThat(facilities).hasSize(count + 1);

        /*dbFacility = facilities.get(facilities.size()-1);
        assertThat(dbFacility.getName()).isEqualTo(NEW_FAC_NAME);
        assertThat(dbFacility.getAddress()).isEqualTo(NEW_FAC_ADR);*/
    }

    /**
     * Not all tree user categories were added
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void noAllUserCategoryAdd() {
        Cinema cinema = new Cinema(NEW_FAC_NAME, NEW_FAC_ADR, "cinema", new HashSet<>(), new HashSet<>(),
                new PointsScale(), new HashSet<>(),  new HashSet<>());
        cinema.getPointsScales().setFacility(cinema);

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(cinema);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
        HashSet<Seat> seats = new HashSet<>();
        seats.add(seat);
        viewingRoom.setSeats(seats);
        cinema.getViewingRooms().add(viewingRoom);

        cinema.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), cinema.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), cinema.getPointsScales()))));


        int count = facilitiesService.findAllFacilities().size();

        DB_INIT = false;

        Facility dbFacility = facilitiesService.add(cinema);
        assertThat(dbFacility).isNotNull();

        List<FacilityDTO> facilities = facilitiesService.findAllFacilities();
        assertThat(facilities).hasSize(count + 1);

        /*dbFacility = facilities.get(facilities.size()-1);
        assertThat(dbFacility.getName()).isEqualTo(NEW_FAC_NAME);
        assertThat(dbFacility.getAddress()).isEqualTo(NEW_FAC_ADR);*/
    }

    @Test
    public void getRepertoireById() {
        List<Projection> projections = facilitiesService.getRepertoireById(DB_FAC_ID);
        assertThat(!projections.isEmpty());
        assertThat(projections).hasSize(2);

        for (Projection p: projections) {
            assertThat(p.getFacility().getId()).isEqualTo(DB_FAC_ID);
        }


    }

    @Test
    public void getRepertoireByIdUnknowID() {
        List<Projection> projections = facilitiesService.getRepertoireById(DB_UNKNOWN_ID);
        assertThat(projections.isEmpty());
        assertThat(projections).hasSize(0);
    }

    @Test
    public void findFacilityByType() {
        List<Facility> theaters = facilitiesService.findFacilityByType("theater");
        List<Facility> cinemas = facilitiesService.findFacilityByType("cinema");
        assertThat(!theaters.isEmpty());
        assertThat(!cinemas.isEmpty());
    }

    @Test
    @Transactional
    public void save()
    {
        Facility cinema = facilitiesService.getFacilityById(DB_FAC_ID);

        String newAdress = "newAddress2";
        String newDescription = "This is a new description for cinema";
        String newName = "newName";
        cinema.setAddress(newAdress);
        cinema.setDescription(newDescription);
        cinema.setName(newName);

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(cinema);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);

        viewingRoomRepository.save(viewingRoom);
        seatRepository.save(seat);


        RegisteredUser user = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
                new HashSet<Ticket>(), new ArrayList<Friendship>());


        userRepository.save(user);


        Projection p = new Projection( "name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>() );

        projectionRepository.save(p);

        Set<Ticket> tickets = cinema.getTickets();
        Ticket t = new Ticket(1L, SeatStatus.FREE, false, user, seat, p, cinema);
        tickets.add(t);

        cinema.setTickets(tickets);


        facilitiesService.save(cinema);

        cinema = facilitiesService.getFacilityById(DB_FAC_ID);
        assertThat(cinema.getAddress()).isEqualTo(newAdress);
        assertThat(cinema.getDescription()).isEqualTo(newDescription);
        assertThat(cinema.getName()).isEqualTo(newName);
        assertThat(cinema.getTickets()).hasSize(1);
    }


    @Test
    public void getFastTickets()
    {
        Facility cinema = facilitiesService.getFacilityById(DB_FAC_ID);

        ViewingRoom viewingRoom = new ViewingRoom();
        viewingRoom.setName(NEW_VM_NAME);
        viewingRoom.setFacility(cinema);
        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);

        viewingRoomRepository.save(viewingRoom);
        seatRepository.save(seat);


        RegisteredUser user = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
                new HashSet<Ticket>(), new ArrayList<Friendship>());


        userRepository.save(user);


        Projection p = new Projection( "name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>() );

        projectionRepository.save(p);



        Set<Ticket> tickets = cinema.getTickets();
        Ticket t = new Ticket(1L, SeatStatus.FREE, false, user, seat, p, cinema);
        t.setFastReservation(true);



        List<Ticket> ticks = ticketRepository.getFastTickets(cinema.getId());
        assertThat(ticks).hasSize(0);

        tickets.add(t);

        ticketRepository.saveAll(tickets);
        cinema.setTickets(tickets);

        ticks = ticketRepository.getFastTickets(cinema.getId());
        assertThat(ticks).hasSize(1);


    }


//    @Test
//    public void getFastTicketsUnknownUser()
//    {
//        Facility cinema = facilitiesService.getFacilityById(DB_FAC_ID);
//
//        ViewingRoom viewingRoom = new ViewingRoom();
//        viewingRoom.setName(NEW_VM_NAME);
//        viewingRoom.setFacility(cinema);
//        Seat seat = new Seat("1", "1", SegmentEnum.NORMAL, viewingRoom);
//
//        viewingRoomRepository.save(viewingRoom);
//        seatRepository.save(seat);
//
//
//        RegisteredUser user = new RegisteredUser("Name", "SurName", "email@gmail.com", "username", "pass", "pic1",
//                false, "0104041", "UsersAddress", new HashSet<PropsReservation>(),
//                new HashSet<Ticket>(), new ArrayList<Friendship>());
//
//
//
//        Projection p = new Projection( "name2", LocalDate.now(), 222, new HashSet<String>(),
//                "genre2", "director2", 22, "picture2", "description2",
//                viewingRoom, new HashSet<Ticket>(), cinema, new HashSet<Feedback>() );
//
//        projectionRepository.save(p);
//
//
//
//        Set<Ticket> tickets = cinema.getTickets();
//        Ticket t = new Ticket(1L, SeatStatus.FREE, false, user, seat, p, cinema);
//        t.setFastReservation(true);
//
//        DB_INIT = false;
//
//        List<Ticket> ticks = ticketRepository.getFastTickets(cinema.getId());
//        assertThat(ticks).hasSize(0);
//
//        tickets.add(t);
//
//        ticketRepository.saveAll(tickets);
//        cinema.setTickets(tickets);
//
//        ticks = ticketRepository.getFastTickets(cinema.getId());
//        assertThat(ticks).hasSize(1);
//
//
//    }
}