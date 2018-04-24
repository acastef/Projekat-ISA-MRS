package bioskopi.rs.controllers;


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
                .andExpect(jsonPath("$.[*].id").value(hasItem(DB_ID.intValue())))
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
                .andExpect(jsonPath("$.id").value((DB_ID.intValue())))
                .andExpect(jsonPath("$.description").value((DB_DESCRIPTION)))
                .andExpect(jsonPath("$.image").value((DB_IMG)))
                .andExpect(jsonPath("$.location").value((DB_LOC)))
                .andExpect(jsonPath("$.quantity").value((DB_QUAN.intValue())));
    }
}
