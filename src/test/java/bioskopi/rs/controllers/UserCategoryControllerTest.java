package bioskopi.rs.controllers;

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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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

import static bioskopi.rs.constants.UserCategoryConstants.DB_COUNT;
import static bioskopi.rs.domain.Privilege.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserCategoryControllerTest {

    private static final String URL_PREFIX = "/user_category";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {


        Cinema cin1 = new Cinema("KMP", "addr3", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

        Cinema cin2 = new Cinema("PMK", "addr4", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());

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

}