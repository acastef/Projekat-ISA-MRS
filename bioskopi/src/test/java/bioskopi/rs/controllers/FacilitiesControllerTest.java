package bioskopi.rs.controllers;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.Theater;
import bioskopi.rs.repository.FacilityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static bioskopi.rs.constants.FacilitiesConstants.*;
import static bioskopi.rs.constants.FacilitiesConstants.DB_INIT;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacilitiesControllerTest {

    private static final String URL_PREFIX = "/facilities";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    public void setUp() throws Exception {
        if (!DB_INIT) {
            facilityRepository.deleteAll();
            Cinema cinema = new Cinema(DB_FAC_NAME,DB_FAC_ADR,"cinema",new HashSet<>(),new HashSet<>(),
                    new PointsScale(),new HashSet<>());

            Theater theater = new Theater("FAC_FAC2","FAC_ADDR2","theater",
                    new HashSet<>(),new HashSet<>(), new PointsScale(),new HashSet<>());

            cinema.getPointsScales().setFacility(cinema);
            theater.getPointsScales().setFacility(theater);

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
    public void getRepertoireById() {
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_FAC_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_FAC_ID))
                .andExpect(jsonPath("$.name").value(DB_FAC_NAME))
                .andExpect(jsonPath("$.address").value(DB_FAC_ADR));
    }

    @Test
    public void addCinema() {
    }

    @Test
    public void addTheater() {
    }
}