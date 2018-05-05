package bioskopi.rs.controllers;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.UserCategory;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PointsScaleRepository;
import bioskopi.rs.util.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static bioskopi.rs.constants.PointsScaleConstants.*;
import static bioskopi.rs.constants.PropsConstants.DB_INIT;
import static bioskopi.rs.domain.Privilege.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointsScaleControllerTest {

    private static final String URL_PREFIX = "/points_scale";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private PointsScaleRepository pointsScaleRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {
        if (!DB_INIT) {

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
            if(!temp.isEmpty()){
                for (Facility fac :
                        temp) {
                    if (fac.getName().compareTo(DB_FAC_NAME) == 0) {
                        DB_FC = fac;
                        DB_PS_ID = fac.getPointsScales().getId();
                    }
                }
            }

            DB_INIT = true;
        }
    }

    @Autowired
    private WebApplicationContext webApplicationContext;


    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_COUNT)));
    }

    @Test
    public void getByName() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_PS_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_PS_ID))
                .andExpect(jsonPath("$.userCategories.[*].name").value(hasItem(DB_TYPE)))
                .andExpect(jsonPath("$.userCategories.[*].points").value(hasItem(DB_PNT_G.intValue())))
                .andExpect(jsonPath("$.userCategories.[*].discount").value(hasItem(DB_DIS_G.doubleValue())));
    }

    @Test
    @Transactional
    public void save() throws Exception {
        PointsScale scale = new PointsScale(DB_PS_ID,new HashSet<>(Arrays.asList(
                new UserCategory(GOLD,NEW_PNT_G,NEW_DIS_G,DB_FC.getPointsScales()),
                new UserCategory(SILVER,40,new BigDecimal("40.56"),DB_FC.getPointsScales()),
                new UserCategory(BRONZE,20,new BigDecimal("20.56"),DB_FC.getPointsScales())
        )),DB_FC);

        String json = TestUtil.json(scale);
        mockMvc.perform(put(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isCreated());

    }

    /**
     * Negative gold points value
     */
    @Test
    @Transactional
    public void wrongPointsValueSave() throws Exception{
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD,-60L,new BigDecimal("60.56"),DB_FC.getPointsScales()),
                        new UserCategory(SILVER,40L,new BigDecimal("40.56"),DB_FC.getPointsScales()),
                        new UserCategory(BRONZE,20L,new BigDecimal("20.56"),DB_FC.getPointsScales())
                ))
                ,DB_FC);
        DB_INIT = false;
        String json = TestUtil.json(scale);
        mockMvc.perform(put(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());

    }

    /**
     * Negative gold discount value
     */
    @Test
    @Transactional
    public void wrongDiscountValueSave() throws Exception {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD,60,new BigDecimal("-60.56"),DB_FC.getPointsScales()),
                        new UserCategory(SILVER,40,new BigDecimal("40.56"),DB_FC.getPointsScales()),
                        new UserCategory(BRONZE,20,new BigDecimal("20.56"),DB_FC.getPointsScales())
                ))
                ,DB_FC);
        DB_INIT = false;
        String json = TestUtil.json(scale);
        mockMvc.perform(put(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }


    /**
     * Silver points value is greater than gold points value
     */
    @Test
    @Transactional
    public void wrongTypePointsValueSave() throws Exception {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD,60,new BigDecimal("60.56"),DB_FC.getPointsScales()),
                        new UserCategory(SILVER,80,new BigDecimal("40.56"),DB_FC.getPointsScales()),
                        new UserCategory(BRONZE,20,new BigDecimal("20.56"),DB_FC.getPointsScales())
                ))
                ,DB_FC);
        DB_INIT = false;
        String json = TestUtil.json(scale);
        mockMvc.perform(put(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    /**
     * Bronze discount value is greater than silver discount value
     */
    @Test
    @Transactional
    public void wrongTypeDiscountValueSave() throws Exception {
        PointsScale scale = new PointsScale(DB_PS_ID,
                new HashSet<>(Arrays.asList(
                        new UserCategory(GOLD,60,new BigDecimal("60.56"),DB_FC.getPointsScales()),
                        new UserCategory(SILVER,40,new BigDecimal("40.56"),DB_FC.getPointsScales()),
                        new UserCategory(BRONZE,20,new BigDecimal("50.56"),DB_FC.getPointsScales())
                ))
                ,DB_FC);
        DB_INIT = false;
        String json = TestUtil.json(scale);
        mockMvc.perform(put(URL_PREFIX + "/save")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

}