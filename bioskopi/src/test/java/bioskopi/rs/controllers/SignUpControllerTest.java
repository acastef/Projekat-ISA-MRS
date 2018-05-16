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

//    private static final String URL_PREFIX = "/signup";
//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Before
//    public void setUp() throws Exception{
//        List<RegisteredUser> registered = new ArrayList<RegisteredUser>();
//        Person person2 = new Person( 2L, "Filip", "Baturan", "filip.baturan@gmail.com");
//        registered.add(new RegisteredUser("filipbat", "2807", "filbat", 2L,
//                new HashSet<PropsReservation>(), person2));
//        List<RegisteredUser> temp = userRepository.saveAll(registered);
//
//    }
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @PostConstruct
//    public void setup() {this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}
//
//    @Test
//    public void add() throws Exception{
//        Person person1 = new Person( 1L, "Aleksandar", "Stefanovic", "stefkic.jr@gmail.com");
//        RegisteredUser reg = new RegisteredUser("acastef", "1611", "stefaca", 1L,
//                new HashSet<PropsReservation>(), person1);
//        String json = TestUtil.json(reg);
//        int index = json.indexOf("}");
//        json = json.substring(0, index);
//        mockMvc.perform(post(URL_PREFIX + "/addUser")
//                .contentType(contentType).content(json))
//                .andExpect(status().isCreated());
//
//    }
}

