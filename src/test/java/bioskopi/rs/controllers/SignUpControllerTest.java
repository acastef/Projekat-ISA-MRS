package bioskopi.rs.controllers;

import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.RegisteredUser;
//import bioskopi.rs.domain.Person;
import bioskopi.rs.repository.UserRepository;
import bioskopi.rs.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SignUpControllerTest {

    private static final String URL_PREFIX = "/signup";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception{
        List<RegisteredUser> registered = new ArrayList<RegisteredUser>();
        RegisteredUser person2 = new RegisteredUser( 2L, "aaa", "aaa",
                "nesto@gmail.com", "ccc", "bbb", "bbb",
                false, "322523432", "adr2");
        registered.add(person2);
        List<RegisteredUser> temp = userRepository.saveAll(registered);

    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}

    @Test
    public void addBad() throws Exception{
        RegisteredUser person1 = new RegisteredUser( 1L, "Aleksandar", "Stefanovic",
                "stefkic.jr@gmail.com", "acastef", "aca", "acastef",
                false, "039202933", "adresa");
        String json = TestUtil.json(person1);
        int index = json.indexOf("}");
        json = json.substring(0, index);
        mockMvc.perform(post(URL_PREFIX + "/addUser")
                .contentType(contentType).content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void activate() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/acastef"))
                .andExpect(status().isOk());

    }
}

