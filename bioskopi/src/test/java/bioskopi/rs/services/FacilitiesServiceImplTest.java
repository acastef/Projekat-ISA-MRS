package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.DTO.FacilityDTO;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.FacilityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static bioskopi.rs.constants.FacilitiesConstants.*;
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

        for (Projection p : projections) {
            //assertThat(p.getFacility().getId() == DB_FAC_ID);
        }

    }

    @Test
    public void findFacilityByType() {
        List<Facility> theaters = facilitiesService.findFacilityByType("theater");
        List<Facility> cinemas = facilitiesService.findFacilityByType("cinema");
        assertThat(!theaters.isEmpty());
        assertThat(!cinemas.isEmpty());
    }
}