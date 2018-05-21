package bioskopi.rs.controllers;

import bioskopi.rs.domain.Ad;
import bioskopi.rs.domain.Bid;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.repository.AdRepository;
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

import static bioskopi.rs.constants.AdConstants.*;
import static bioskopi.rs.constants.AdConstants.AD;
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
public class AdControllerTest {

    private static final String URL_PREFIX = "/ads";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

        RegisteredUser user1 = new RegisteredUser("user3", "user", "user", "user",
                "user", "user",true,"user","user", new HashSet<>(),
                new HashSet<>(),new HashSet<>());

        RegisteredUser user2 = new RegisteredUser("user4", "user4", "user4", "user4",
                "user4", "user4",true,"user4","user4", new HashSet<>(),
                new HashSet<>(),new HashSet<>());


        RUSER1 = registeredUserRepository.save(user1);
        RUSER2 = registeredUserRepository.save(user2);


        Ad ad1 = new Ad(DB_IMG1,DB_NM1,DB_DS1,DB_DL1,DB_ST1,RUSER1,new HashSet<>(),DB_VR1);
        Ad ad2 = new Ad(DB_IMG2,DB_NM2,DB_DS2,DB_DL2,DB_ST2,RUSER1,new HashSet<>(),DB_VR2);

        AD = adRepository.saveAll(new ArrayList<Ad>(){{
            add(ad1);
            add(ad2);
        }}).get(0);

    }

    @Autowired
    private WebApplicationContext webApplicationContext;


    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllActive() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all/active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_COUNT1)));
    }

    @Test
    public void getAllWait() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/all/wait"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_COUNT2)));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/get/" + AD.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(AD.getId()))
                .andExpect(jsonPath("$.description").value(DB_DS1))
                .andExpect(jsonPath("$.version").value(DB_VR1));
    }


    @Test
    public void add() throws Exception {
        Ad ad3 = new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,RUSER1,new HashSet<>(),NEW_VR);
        String json = TestUtil.json(ad3);

        mockMvc.perform(post(URL_PREFIX + "/add")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void changeAd() throws Exception {
        Ad ad3 = new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,RUSER1,new HashSet<>(),NEW_VR);
        String json = TestUtil.json(ad3);

        mockMvc.perform(put(URL_PREFIX + "/change")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void acceptAd() throws Exception {
        String json = TestUtil.json(AD);

        mockMvc.perform(put(URL_PREFIX + "/accept")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void rejectAd() throws Exception {
        String json = TestUtil.json(AD);

        mockMvc.perform(put(URL_PREFIX + "/reject")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAd() throws Exception {
        String json = TestUtil.json(AD);

        mockMvc.perform(put(URL_PREFIX + "/delete")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addBid() throws Exception {
        Bid bid = new Bid(25.36,AD.getDeadline().minusMinutes(12),RUSER2,
                new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,new RegisteredUser(),new HashSet<>(),NEW_VR));
        String json = TestUtil.json(bid);

        mockMvc.perform(post(URL_PREFIX + "/bid")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }
}