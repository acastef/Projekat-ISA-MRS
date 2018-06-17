package bioskopi.rs.services;


import bioskopi.rs.domain.*;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.ProjectionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static bioskopi.rs.constants.ProjectionsConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectionServiceTest {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private ProjectionRepository projectionRepository;


    @Before
    @Transactional
    public void setUp() throws Exception {


        Cinema cin1 = new Cinema("loc1", "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        Cinema cin2 = new Cinema("PRESERVATION", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        cin1.getPointsScales().setFacility(cin1);
        cin2.getPointsScales().setFacility(cin2);

        Facility fac = facilityRepository.save(cin1);
        DB_FAC = fac;
        DB_FAC_ID = fac.getId();

        facilityRepository.save(cin2);

        Projection p = new Projection("name1", LocalDateTime.now(), 111, new HashSet<String>(),
                "genre1", "director1", 11, "picture1", "description1",
                viewingRoom, new HashSet<Ticket>(), DB_FAC, new HashSet<Feedback>());

        projectionRepository.save(p);
    }


    @Test
    @Transactional
    public void add() {

        Projection p2 = new Projection("name2", LocalDateTime.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                viewingRoom, new HashSet<Ticket>(), DB_FAC, new HashSet<Feedback>());

        Long idP2 = projectionRepository.save(p2).getId();

        Projection newP2 = projectionRepository.getOne(idP2);
        assertThat(newP2.getName()).isEqualTo(p2.getName());
        assertThat(newP2.getDescription()).isEqualTo(p2.getDescription());
        assertThat(newP2.getGenre()).isEqualTo(p2.getGenre());

    }
}
