package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.repository.FacilityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static bioskopi.rs.constants.FacilitiesConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacilitiesServiceImplTest {

    @Autowired
    private FacilitiesService facilitiesService;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {
        if (!DB_INIT) {

            Cinema cinema = new Cinema(DB_FAC_NAME,DB_FAC_ADR,"cinema",new HashSet<>(),new HashSet<>(),
                    new PointsScale(),new HashSet<>());

            Theater theater = new Theater("FAC_FAC2","FAC_ADDR2","theater",
                    new HashSet<>(),new HashSet<>(), new PointsScale(),new HashSet<>());

            cinema.getPointsScales().setFacility(cinema);
            theater.getPointsScales().setFacility(theater);


            Projection projection1 = new Projection("Proj1", cinema);
            Projection projection2 = new Projection("Proj2", cinema);
            Projection projection3 = new Projection("Proj3", theater);

            List<Facility> temp = facilityRepository.saveAll(new ArrayList<Facility>() {{
                add(cinema);
                add(theater);
            }});
            if(!temp.isEmpty()){
                for (Facility fac :
                        temp) {
                    if (fac.getName().compareTo(DB_FAC_NAME) == 0) {
                        DB_FAC_ID = fac.getId();
                    }
                }
            }

            DB_INIT = true;
        }
    }

    @Test
    public void findAllFacilities() {
        List<Facility> allFacilities = facilitiesService.findAllFacilities();
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
    public void add() {
    }

    @Test
    public void getRepertoireById() {
        List<Projection> projections = facilitiesService.getRepertoireById(DB_FAC_ID);
        assertThat(!projections.isEmpty());

        for (Projection p: projections) {
            assertThat(p.getFacility().getId() == DB_FAC_ID);
        }

    }

    @Test
    public void findFacilityByType() {
    }
}