package bioskopi.rs.services;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PropsRepository;
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
import java.util.List;

import static bioskopi.rs.constants.PropsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

        DB_FAC = new Cinema(DB_LOC2, "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        Cinema cin2 = new Cinema("PROPS2", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        DB_FAC.getPointsScales().setFacility(DB_FAC);
        cin2.getPointsScales().setFacility(cin2);

        Props props1 = new Props(DB_DESCRIPTION2, DB_IMG1, DB_FAC);
        Props props2 = new Props("mask2", DB_IMG2, cin2);
        Props props3 = new Props("sticker2", DB_IMG3, DB_FAC);

        facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(DB_FAC);
            add(cin2);
        }});

        DB_PROP = propsRepository.save(props1);
        propsRepository.saveAll(new ArrayList<Props>() {{
            add(props2);
            add(props3);
        }});
    }


    @Test
    public void findByDescription() throws Exception {
        PropsDTO props = propsService.findByDescription(DB_DESCRIPTION2);
        assertThat(props).isNotNull();
        assertThat(props.getDescription()).isEqualTo(DB_DESCRIPTION2);
        assertThat(props.getImage()).isEqualTo(DB_IMG);
        assertThat(props.getLocation()).isEqualTo(DB_PLACE2);
    }

    @Test
    public void findAllProps() throws Exception {
        List<PropsDTO> allProps = propsService.findAllProps();
        assertThat(allProps).hasSize(DB_COUNT);
    }

    @Test
    @Transactional
    public void add(){
        Props props = new Props(NEW_DES,NEW_ADR,DB_FAC);
        int count = propsService.findAllProps().size();
        Props temp = propsService.add(props);
        List<PropsDTO> all = propsService.findAllProps();
        assertThat(all).hasSize(count +1);
    }

    /**
     * Facility that is in relation with props does not exist
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void wrongFacilityAdd(){
        Props props = new Props(NEW_DES,NEW_ADR,new Facility());
        propsService.add(props);
    }

    @Test
    @Transactional
    public void delete(){
        propsService.delete(DB_PROP);
        PropsDTO temp = propsService.findById(DB_PROP.getId());
        assertThat(temp.isActive()).isEqualTo(false);
    }

    /**
     * Props does not exist in database
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void noPropsDelete(){
        propsService.delete(new Props());
    }

}