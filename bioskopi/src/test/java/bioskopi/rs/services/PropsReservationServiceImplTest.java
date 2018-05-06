package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PropsRepository;
import bioskopi.rs.repository.RegisteredUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;

import static bioskopi.rs.constants.PropsConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropsReservationServiceImplTest {

    @Autowired
    private PropsReservationService propsReservationService;

    @Autowired
    private PropsRepository propsRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {


        registeredUserRepository.deleteAll();

        Cinema cin1 = new Cinema(DB_LOC3, "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        Cinema cin2 = new Cinema("PRESERVATION", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        cin1.getPointsScales().setFacility(cin1);
        cin2.getPointsScales().setFacility(cin2);

        Props props1 = new Props(DB_DESCRIPTION3, DB_IMG1, cin1);
        Props props2 = new Props("mask3", DB_IMG2, cin2);
        Props props3 = new Props("sticker3", DB_IMG3, cin1);

        RegisteredUser user = new RegisteredUser("user3", "user", "user", new HashSet<>(),
                new Person("test", "test", "test3"));

        facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(cin1);
            add(cin2);
        }});

        propsList = propsRepository.saveAll(new ArrayList<Props>() {{
            add(props1);
            add(props2);
            add(props3);
        }});

        registeredUser = registeredUserRepository.save(user);


    }

    @Test
    @Transactional
    public void add() {
        PropsReservation propsReservation = new PropsReservation(propsList.get(0), registeredUser, 1);
        propsReservationService.add(propsReservation);
    }

    @Test
    public void getByUserIdAndPropsId() {
        propsReservationService.getByUserIdAndPropsId(registeredUser.getId(), propsList.get(0).getId());

    }
}