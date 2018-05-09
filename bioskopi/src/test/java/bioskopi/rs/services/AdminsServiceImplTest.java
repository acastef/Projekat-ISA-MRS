package bioskopi.rs.services;

import bioskopi.rs.domain.CaTAdmin;
import bioskopi.rs.domain.FanZoneAdmin;
import bioskopi.rs.repository.CaTAdminRepository;
import bioskopi.rs.repository.FanZoneAdminRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static bioskopi.rs.constants.AdminsConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminsServiceImplTest {

    @Autowired
    private AdminsService adminsService;

    @Autowired
    private FanZoneAdminRepository fanZoneAdminRepository;

    @Autowired
    private CaTAdminRepository caTAdminRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

        FanZoneAdmin fan1 = new FanZoneAdmin(DB_FAN_UN,DB_FAN_PS,DB_CT_AV,DB_FAN_FL,DB_FAN_PR);

        CaTAdmin ct1 = new CaTAdmin(DB_CT_UN,DB_CT_PS,DB_CT_AV,DB_CT_FL,DB_CT_PR);

        fanZoneAdminRepository.save(fan1);
        caTAdminRepository.save(ct1);
    }

    @Test
    public void getAllFanZoneAdmins() {
    }

    @Test
    public void getByIdFanZoneAdmin() {
    }

    @Test
    public void addFanZoneAdmin() {
    }

    @Test
    public void getAllCaTAdmins() {
    }

    @Test
    public void getByIdCaTAdmins() {
    }

    @Test
    public void addCaTAdmin() {
    }
}