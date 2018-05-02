package bioskopi.rs.controllers;


import bioskopi.rs.domain.*;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PropsRepository;
import bioskopi.rs.services.PropsServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
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
import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static bioskopi.rs.constants.PropsConstants.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropsControllerTest {

    private static final String URL_PREFIX = "/props";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private PropsRepository propsRepository;

    @Autowired
    private FacilityRepository facilityRepository;


    @Before
    @Transactional
    public void setUp() throws Exception {

        if (!DB_INIT) {
            /*Cinema cin1 = new Cinema(1L, DB_LOC, "addr1", "cinema",
                    new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(), new HashSet<>());

            Cinema cin2 = new Cinema(2L, "Arena", "addr2", "cinema",
                    new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(), new HashSet<>());

            cin1.getPointsScales().setFacility(cin1);
            cin2.getPointsScales().setFacility(cin2);

            Props props1 = new Props(DB_DESCRIPTION, DB_QUAN, DB_IMG1, cin1);
            Props props2 = new Props("mask", 7, DB_IMG2, cin2);
            Props props3 = new Props("sticker", 6, DB_IMG3, cin1);

            //cin1.getProps().add(props1); cin1.getProps().add(props3); cin2.getProps().add(props2);

            facilityRepository.saveAll(new ArrayList<Facility>() {{
                add(cin1);
                add(cin2);
            }});

            */

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
                .andExpect(jsonPath("$", hasSize(DB_COUNT)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DB_DESCRIPTION)))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DB_IMG)))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DB_LOC)))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DB_QUAN.intValue())));
    }

    @Test
    public void getByDescription() throws Exception {

        mockMvc.perform(get(URL_PREFIX + "/" + DB_DESCRIPTION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.description").value((DB_DESCRIPTION)))
                .andExpect(jsonPath("$.image").value((DB_IMG)))
                .andExpect(jsonPath("$.location").value((DB_LOC)))
                .andExpect(jsonPath("$.quantity").value((DB_QUAN.intValue())));
    }
}
