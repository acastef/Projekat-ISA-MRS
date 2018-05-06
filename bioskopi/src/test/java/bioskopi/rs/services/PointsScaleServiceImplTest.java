package bioskopi.rs.services;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.UserCategory;
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

import static bioskopi.rs.constants.PointsScaleConstants.*;
import static bioskopi.rs.domain.Privilege.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PointsScaleServiceImplTest {

    @Autowired
    private PointsScaleService pointsScaleService;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

        Cinema cin1 = new Cinema(DB_FAC_NAME, "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        Cinema cin2 = new Cinema("PS2", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        cin1.getPointsScales().setFacility(cin1);
        cin2.getPointsScales().setFacility(cin2);

        cin1.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, DB_PNT_G, DB_DIS_G, cin1.getPointsScales()),
                new UserCategory(SILVER, DB_PNT_S, DB_DIS_S, cin1.getPointsScales()),
                new UserCategory(BRONZE, DB_PNT_B, DB_DIS_B, cin1.getPointsScales()))));

        cin2.getPointsScales().setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, 70L, new BigDecimal("36.11"), cin2.getPointsScales()),
                new UserCategory(SILVER, 50L, new BigDecimal("29.16"), cin2.getPointsScales()),
                new UserCategory(BRONZE, 30L, new BigDecimal("15.83"), cin2.getPointsScales()))));

        List<Facility> temp = facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(cin1);
            add(cin2);
        }});
        if (!temp.isEmpty()) {
            for (Facility fac :
                    temp) {
                if (fac.getName().compareTo(DB_FAC_NAME) == 0) {
                    DB_FC = fac;
                    DB_PS_ID = fac.getPointsScales().getId();
                }
            }
        }
    }

    @Test
    public void getAll() {
        List<PointsScale> allScales = pointsScaleService.getAll();
        assertThat(allScales).hasSize(DB_COUNT);
    }

    @Test
    public void getById() {
        PointsScale scale = pointsScaleService.getById(DB_PS_ID);
        assertThat(scale).isNotNull();
        assertThat(scale.getId()).isEqualTo(DB_PS_ID);
    }

    @Test
    @Transactional
    public void save() {
        PointsScale scale = pointsScaleService.getById(DB_PS_ID);

        scale.setUserCategories(new HashSet<>(Arrays.asList(
                new UserCategory(GOLD, NEW_PNT_G, NEW_DIS_G, DB_FC.getPointsScales()),
                new UserCategory(SILVER, 40, new BigDecimal("40.56"), DB_FC.getPointsScales()),
                new UserCategory(BRONZE, 20, new BigDecimal("20.56"), DB_FC.getPointsScales())
        )));
        pointsScaleService.save(scale);

        scale = pointsScaleService.getById(DB_PS_ID);
        for (UserCategory category :
                scale.getUserCategories()) {
            if (category.getName() == GOLD) {
                assertThat(category.getPoints()).isEqualTo(NEW_PNT_G);
                assertThat(category.getDiscount().doubleValue()).isEqualTo(NEW_DIS_G.doubleValue());
            }
        }
    }

    /**
     * Negative gold points value
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void wrongPointsValueSave() {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD, -60L, new BigDecimal("60.56"), DB_FC.getPointsScales()),
                        new UserCategory(SILVER, 40L, new BigDecimal("40.56"), DB_FC.getPointsScales()),
                        new UserCategory(BRONZE, 20L, new BigDecimal("20.56"), DB_FC.getPointsScales())
                ))
                , DB_FC);
        DB_INIT = false;
        pointsScaleService.save(scale);

    }

    /**
     * Negative gold discount value
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void wrongDiscountValueSave() {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD, 60, new BigDecimal("-60.56"), DB_FC.getPointsScales()),
                        new UserCategory(SILVER, 40, new BigDecimal("40.56"), DB_FC.getPointsScales()),
                        new UserCategory(BRONZE, 20, new BigDecimal("20.56"), DB_FC.getPointsScales())
                ))
                , DB_FC);
        DB_INIT = false;
        pointsScaleService.save(scale);
    }

    /**
     * Silver points value is greater than gold points value
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void wrongTypePointsValueSave() {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD, 60, new BigDecimal("60.56"), DB_FC.getPointsScales()),
                        new UserCategory(SILVER, 80, new BigDecimal("40.56"), DB_FC.getPointsScales()),
                        new UserCategory(BRONZE, 20, new BigDecimal("20.56"), DB_FC.getPointsScales())
                ))
                , DB_FC);
        DB_INIT = false;
        pointsScaleService.save(scale);
    }

    /**
     * Bronze discount value is greater than silver discount value
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void wrongTypeDiscountValueSave() {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD, 60, new BigDecimal("60.56"), DB_FC.getPointsScales()),
                        new UserCategory(SILVER, 40, new BigDecimal("40.56"), DB_FC.getPointsScales()),
                        new UserCategory(BRONZE, 20, new BigDecimal("50.56"), DB_FC.getPointsScales())
                ))
                , DB_FC);
        DB_INIT = false;
        pointsScaleService.save(scale);
    }

}