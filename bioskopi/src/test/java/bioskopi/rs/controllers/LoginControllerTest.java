package bioskopi.rs.controllers;

import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.Person;
import bioskopi.rs.repository.UserRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginControllerTest {

    private static final String URL_PREFIX = "/login";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception{
        List<RegisteredUser> registered = new ArrayList<RegisteredUser>();
        Person person1 = new Person( 1L, "Aleksandar", "Stefanovic", "stefkic.jr@gmail.com");
        registered.add(new RegisteredUser("acastef", "1611", "stefaca", 1L,
                new HashSet<PropsReservation>(), person1));
        Person person2 = new Person( 2L, "Filip", "Baturan", "filip.baturan@gmail.com");
        registered.add(new RegisteredUser("filipbat", "2807", "filbat", 2L,
                new HashSet<PropsReservation>(), person2));
        List<RegisteredUser> temp = userRepository.saveAll(registered);

    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}

    @Test
    public void getUsers() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/allUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void findUserWithUsername() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/" + "acastef"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username").value("acastef"))
                .andExpect(jsonPath("$.password").value("1611"))
                .andExpect(jsonPath("$.avatar").value("stefaca"));

    }
}
