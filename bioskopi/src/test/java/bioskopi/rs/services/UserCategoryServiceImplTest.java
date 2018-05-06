package bioskopi.rs.services;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.UserCategory;
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

import static bioskopi.rs.constants.UserCategoryConstants.DB_COUNT;
import static bioskopi.rs.domain.Privilege.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserCategoryServiceImplTest {


    @Autowired
    private UserCategoryService userCategoryService;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {


        Cinema cin1 = new Cinema("KMP", "addr3", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        Cinema cin2 = new Cinema("PMK", "addr4", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        cin1.getPointsScales().setFacility(cin1);
        cin2.getPointsScales().setFacility(cin2);

        cin1.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 80, new BigDecimal("0.0"), cin1.getPointsScales()),
                new UserCategory(SILVER, 60, new BigDecimal("0.0"), cin1.getPointsScales()),
                new UserCategory(BRONZE, 40, new BigDecimal("0.0"), cin1.getPointsScales()))));

        cin2.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70, new BigDecimal("0.0"), cin2.getPointsScales()),
                new UserCategory(SILVER, 50, new BigDecimal("0.0"), cin2.getPointsScales()),
                new UserCategory(BRONZE, 30, new BigDecimal("0.0"), cin2.getPointsScales()))));

        facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(cin1);
            add(cin2);
        }});
    }


    @Test
    public void findAll() {
        List<UserCategory> categories = userCategoryService.findAll();
        assertThat(categories).hasSize(DB_COUNT);
    }
}