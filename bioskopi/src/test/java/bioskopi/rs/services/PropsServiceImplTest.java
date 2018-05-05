package bioskopi.rs.services;

import bioskopi.rs.constants.PropsConstants;
import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.Props;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PropsRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static bioskopi.rs.constants.PropsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropsServiceImplTest {

    @Autowired
    PropsService propsService;

    @Autowired
    private PropsRepository propsRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

        if (!DB_INIT_P) {
            Cinema cin1 = new Cinema( DB_LOC, "addr1", "cinema",
                    new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

            Cinema cin2 = new Cinema( "Arena", "addr2", "cinema",
                    new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

            cin1.getPointsScales().setFacility(cin1);
            cin2.getPointsScales().setFacility(cin2);

            Props props1 = new Props(DB_DESCRIPTION, DB_IMG1, cin1);
            Props props2 = new Props("mask" , DB_IMG2, cin2);
            Props props3 = new Props("sticker", DB_IMG3, cin1);

            facilityRepository.saveAll(new ArrayList<Facility>() {{
                add(cin1);
                add(cin2);
            }});

            propsRepository.saveAll(new ArrayList<Props>() {{
                add(props1);
                add(props2);
                add(props3);
            }});


            DB_INIT_P = true;
        }
    }


    @Test
    public void findByDescription() throws Exception {
        PropsDTO props = propsService.findByDescription(DB_DESCRIPTION);
        assertThat(props).isNotNull();
        assertThat(props.getDescription()).isEqualTo(DB_DESCRIPTION);
        assertThat(props.getImage()).isEqualTo(DB_IMG);
        assertThat(props.getLocation()).isEqualTo(DB_PLACE );
    }

    @Test
    public void findAllProps() throws Exception {
        List<PropsDTO> allProps = propsService.findAllProps();
        assertThat(allProps).hasSize(DB_COUNT);
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        if(DB_INIT_P) {
            propsRepository.deleteAll();
            facilityRepository.deleteAll();
            DB_INIT_P = false;
        }
    }
}