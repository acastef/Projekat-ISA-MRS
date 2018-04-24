package bioskopi.rs.services;

import bioskopi.rs.domain.UserCategory;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static bioskopi.rs.constants.UserCategoryConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCategoryServiceImplTest {


    @Autowired
    private UserCategoryService userCategoryService;

    @Test
    public void findAll() {
        List<UserCategory> categories = userCategoryService.findAll();
        assertThat(categories).hasSize(DB_COUNT);
    }

    @Test
    public void findByName() {
        UserCategory category = userCategoryService.findByName(DB_NAME);
        assertThat(category).isNotNull();

        assertThat(category.getId()).isEqualTo(DB_ID);
        assertThat(category.getName()).isEqualTo(DB_NAME);
        assertThat(category.getPoints()).isEqualTo(DB_PTS);
        assertTrue(category.getDiscount().compareTo(DB_DSC) == 0);
    }
}