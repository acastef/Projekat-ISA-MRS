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

import java.time.LocalDate;
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
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        Cinema cin2 = new Cinema("PRESERVATION", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        cin1.getPointsScales().setFacility(cin1);
        cin2.getPointsScales().setFacility(cin2);

        Projection p = new Projection( "name2", LocalDate.now(), 222, new HashSet<String>(),
                "genre2", "director2", 22, "picture2", "description2",
                new ViewingRoom(), new HashSet<Ticket>() );

        RegisteredUser user = new RegisteredUser("user3", "user", "user", new HashSet<>(),
                new Person("test", "test", "test3"));

        facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(cin1);
            add(cin2);
        }});

        projection = projectionRepository.save(p);

    }


    @Test
    @Transactional
    public void add() {
        projectionService.add(projection);
    }
}
