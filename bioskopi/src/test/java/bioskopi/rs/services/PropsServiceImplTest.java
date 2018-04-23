package bioskopi.rs.services;

import bioskopi.rs.domain.Props;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static bioskopi.rs.constants.PropsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropsServiceImplTest {

    @Autowired
    PropsService propsService;

    @Test
    public void findByDescription() throws Exception {
        Props props = propsService.findByDescription(DB_DESCRIPTION);
        assertThat(props).isNotNull();

        assertThat(props.getId()).isEqualTo(DB_ID);
        assertThat(props.getDescription()).isEqualTo(DB_DESCRIPTION);
        assertThat(props.getImage()).isEqualTo(DB_IMG);
        assertThat(props.getLocation()).isEqualTo(DB_LOC);
        assertThat(props.getQuantity()).isEqualTo(DB_QUAN);
    }

    @Test
    public void findAllProps() throws Exception {
        List<Props> allProps = propsService.findAllProps();
        assertThat(allProps).hasSize(DB_COUNT);
    }
}