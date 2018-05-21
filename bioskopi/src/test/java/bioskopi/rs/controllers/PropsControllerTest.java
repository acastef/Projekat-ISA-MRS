package bioskopi.rs.controllers;


import bioskopi.rs.domain.*;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PropsRepository;
import bioskopi.rs.repository.RegisteredUserRepository;
import bioskopi.rs.util.TestUtil;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;

import static bioskopi.rs.constants.PropsConstants.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropsControllerTest {

    private static final String URL_PREFIX = "/props";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private PropsRepository propsRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

        DB_FAC = new Cinema(DB_LOC, "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        Cinema cin2 = new Cinema("Arena", "addr2", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>());

        DB_FAC.getPointsScales().setFacility(DB_FAC);
        cin2.getPointsScales().setFacility(cin2);

        Props props1 = new Props(DB_DESCRIPTION, DB_IMG1, DB_FAC);
        Props props2 = new Props("mask", DB_IMG2, cin2);
        Props props3 = new Props("sticker", DB_IMG3, DB_FAC);

        RegisteredUser user = new RegisteredUser("test", "test", "test","user",
                "user", "user",false,"user1","user1",new HashSet<>(),
                new HashSet<>(), new ArrayList<>());

        facilityRepository.saveAll(new ArrayList<Facility>() {{
            add(DB_FAC);
            add(cin2);
        }});

        DB_PROP = propsRepository.save(props1);
        propsList = propsRepository.saveAll(new ArrayList<Props>() {{
            add(props2);
            add(props3);
        }});

        registeredUser = registeredUserRepository.save(user);

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
                .andExpect(jsonPath("$.[*].location").value(hasItem(DB_PLACE)));
    }

    @Test
    public void getByDescription() throws Exception {

        mockMvc.perform(get(URL_PREFIX + "/" + DB_DESCRIPTION))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.description").value((DB_DESCRIPTION)))
                .andExpect(jsonPath("$.image").value((DB_IMG)))
                .andExpect(jsonPath("$.location").value((DB_PLACE)));
    }

    @Test
    @Transactional
    public void addReservation() throws Exception {
        PropsReservation propsReservation = new PropsReservation(propsList.get(0), registeredUser, 1);
        String json = TestUtil.json(propsReservation);
        int index = json.lastIndexOf("}");
        json = json.substring(0,index) + ",\"registeredUser\":" + TestUtil.json(propsReservation.getRegisteredUser())
                + "}";
        mockMvc.perform(post(URL_PREFIX + "/reserve")
                .contentType(contentType).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void add() throws Exception {
        Props props = new Props(NEW_DES,NEW_ADR,DB_FAC);
        String json = TestUtil.json(props);
        int index = json.lastIndexOf("}");
        json = json.substring(0,index) + ",\"facility\":" +  TestUtil.json(props.getFacility()) + "}";
        mockMvc.perform(post(URL_PREFIX + "/add")
                .contentType(contentType).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void wrongFacilityAdd() throws Exception {
        Props props = new Props(NEW_DES,NEW_ADR,new Facility());
        String json = TestUtil.json(props);
        int index = json.lastIndexOf("}");
        json = json.substring(0,index) + ",\"facility\":" +  TestUtil.json(props.getFacility()) + "}";
        mockMvc.perform(post(URL_PREFIX + "/add")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void change() throws Exception {
        DB_PROP.setDescription(NEW_DES);
        String json = TestUtil.json(DB_PROP);
        int index = json.lastIndexOf("}");
        json = json.substring(0,index) + ",\"facility\":" +  TestUtil.json(DB_PROP.getFacility()) + "}";
        mockMvc.perform(put(URL_PREFIX + "/change")
                .contentType(contentType).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void delete() throws Exception {

        String json = TestUtil.json(DB_PROP);
        mockMvc.perform(put(URL_PREFIX + "/delete")
                .contentType(contentType).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void wrongPropsDelete() throws Exception {

        String json = TestUtil.json(new Props());
        mockMvc.perform(put(URL_PREFIX + "/delete")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }
}
