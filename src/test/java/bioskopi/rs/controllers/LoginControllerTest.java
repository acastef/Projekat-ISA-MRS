package bioskopi.rs.controllers;

import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.RegisteredUser;
//import bioskopi.rs.domain.Person;
import bioskopi.rs.domain.User;
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


    //User(long id, String name, String surname, String email, String username, String password, String avatar,
     //    boolean firstLogin, String telephone, String address)

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception{
        List<RegisteredUser> registered = new ArrayList<RegisteredUser>();
        RegisteredUser person1 = new RegisteredUser( 1L, "Aleksandar", "Stefanovic",
                "stefkic.jr@gmail.com", "acastef", "aca", "acastef",
                false, "039202933", "adresa");
        registered.add(person1);
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
    public void getUsers() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/allUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getReg() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/getReg/" + "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.username").value("acastef"))
                .andExpect(jsonPath("$.password").value("aca"))
                .andExpect(jsonPath("$.avatar").value("acastef"));
    }

    @Test
    public void logout() throws Exception{
        mockMvc.perform(get(URL_PREFIX + "/logout"))
                .andExpect(status().isOk());
    }
}
