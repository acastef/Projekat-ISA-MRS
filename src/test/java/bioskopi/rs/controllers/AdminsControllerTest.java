package bioskopi.rs.controllers;

import bioskopi.rs.domain.CaTAdmin;
import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.FanZoneAdmin;
//import bioskopi.rs.domain.Person;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.repository.CaTAdminRepository;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.FanZoneAdminRepository;
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
import java.util.HashSet;

import static bioskopi.rs.constants.AdminsConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminsControllerTest {


    private static final String URL_PREFIX = "/admins";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private FanZoneAdminRepository fanZoneAdminRepository;

    @Autowired
    private CaTAdminRepository caTAdminRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {
        FanZoneAdmin fan1 = new FanZoneAdmin(DB_FAN_NM,DB_FAN_SN,DB_FAN_EM,DB_FAN_UN,DB_FAN_PS,DB_FAN_AV,DB_FAN_FL,
                DB_FAN_TEL,DB_FAN_ADR);


        DB_FAC = new Cinema("Cin1", "addr1", "cinema",
                new HashSet<>(), new HashSet<>(), new PointsScale(), new HashSet<>(),  new HashSet<>());
        DB_FAC.getPointsScales().setFacility(DB_FAC);

        CaTAdmin ct1 = new CaTAdmin(DB_CT_NM,DB_CT_SN,DB_CT_EM,DB_CT_UN,DB_CT_PS,DB_CT_AV,DB_CT_FL,
                DB_CT_TEL,DB_CT_ADR,DB_FAC);

        DB_FAC = facilityRepository.save(DB_FAC);

        DB_FAN_ID =  fanZoneAdminRepository.save(fan1).getId();
        DB_CT_ID = caTAdminRepository.save(ct1).getId();
    }

    @Autowired
    private WebApplicationContext webApplicationContext;


    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllFanZone() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/fan_zone/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_FAN_COUNT)));
    }

    @Test
    public void getByIdFanZone() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/fan_zone/get/" + DB_FAN_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_FAN_ID))
                .andExpect(jsonPath("$.username").value(DB_FAN_UN))
                .andExpect(jsonPath("$.password").value(DB_FAN_PS))
                .andExpect(jsonPath("$.avatar").value(DB_FAN_AV))
                .andExpect(jsonPath("$.firstLogin").value(DB_FAN_FL))
                .andExpect(jsonPath("$.name").value(DB_FAN_NM))
                .andExpect(jsonPath("$.surname").value(DB_FAN_SN))
                .andExpect(jsonPath("$.email").value(DB_FAN_EM));
    }

    @Test
    @Transactional
    public void addFanZone() throws Exception {
        FanZoneAdmin admin = new FanZoneAdmin(NEW_FAN_NM,NEW_FAN_SN,NEW_FAN_EM,NEW_FAN_UN,NEW_FAN_PS,NEW_FAN_AV,
                NEW_FAN_FL,NEW_FAN_TEL,NEW_FAN_ADR);
        String json = TestUtil.json(admin);

        mockMvc.perform(post(URL_PREFIX + "/fan_zone/add")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isCreated());
    }



    @Test
    @Transactional
    public void changeFanZone() throws Exception {
        FanZoneAdmin admin = new FanZoneAdmin(NEW_FAN_NM,NEW_FAN_SN,NEW_FAN_EM,NEW_FAN_UN,NEW_FAN_PS,NEW_FAN_AV,
                NEW_FAN_FL,NEW_FAN_TEL,NEW_FAN_ADR);
        String json = TestUtil.json(admin);

        mockMvc.perform(put(URL_PREFIX + "/fan_zone/change")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isCreated());
    }

   /* @Test
    @Transactional
    public void noUniqueUsernameAddFanZone() throws Exception {
        FanZoneAdmin admin = new FanZoneAdmin(DB_FAN_NM,DB_FAN_SN,DB_FAN_EM,DB_FAN_UN,DB_FAN_PS,DB_FAN_AV,DB_FAN_FL,
                DB_FAN_TEL,DB_FAN_ADR);
        String json = TestUtil.json(admin);

        mockMvc.perform(post(URL_PREFIX + "/fan_zone/add")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }*/


    @Test
    public void getAllCT() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/ct/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_CT_COUNT)));
    }

    @Test
    public void getByIdCT() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/ct/get/" + DB_CT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_CT_ID))
                .andExpect(jsonPath("$.username").value(DB_CT_UN))
                .andExpect(jsonPath("$.password").value(DB_CT_PS))
                .andExpect(jsonPath("$.avatar").value(DB_CT_AV))
                .andExpect(jsonPath("$.firstLogin").value(DB_CT_FL))
                .andExpect(jsonPath("$.name").value(DB_CT_NM))
                .andExpect(jsonPath("$.surname").value(DB_CT_SN))
                .andExpect(jsonPath("$.email").value(DB_CT_EM));
    }

    @Test
    @Transactional
    public void addCT() throws Exception {
        CaTAdmin admin = new CaTAdmin(NEW_CT_NM,NEW_CT_SN,NEW_CT_EM,NEW_CT_UN,NEW_CT_PS,NEW_CT_AV,NEW_CT_FL,
                NEW_CT_TEL,NEW_CT_ADR,DB_FAC);
        String json = TestUtil.json(admin);

        mockMvc.perform(post(URL_PREFIX + "/ct/add")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void changeCT() throws Exception {
        CaTAdmin admin = new CaTAdmin(NEW_CT_NM,NEW_CT_SN,NEW_CT_EM,NEW_CT_UN,NEW_CT_PS,NEW_CT_AV,NEW_CT_FL,
                NEW_CT_TEL,NEW_CT_ADR,DB_FAC);
        String json = TestUtil.json(admin);

        mockMvc.perform(put(URL_PREFIX + "/ct/change")
                .contentType(contentType).content(json))
                .andExpect(status().isForbidden());
                //.andExpect(status().isCreated());
    }

  /*  @Test
    @Transactional
    public void noUniqueUsernameAddCT() throws Exception {
        CaTAdmin admin = new CaTAdmin(NEW_CT_NM,NEW_CT_SN,NEW_CT_EM,DB_CT_UN,NEW_CT_PS,NEW_CT_AV,NEW_CT_FL,
                NEW_CT_TEL,NEW_CT_ADR,DB_FAC);
        String json = TestUtil.json(admin);

        mockMvc.perform(post(URL_PREFIX + "/ct/add")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }*/
}