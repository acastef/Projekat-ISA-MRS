package bioskopi.rs.services;

import bioskopi.rs.domain.CaTAdmin;
import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.FanZoneAdmin;
import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.CaTAdminRepository;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.FanZoneAdminRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;

import static bioskopi.rs.constants.AdminsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;



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

    @Test
    public void getAllFanZoneAdmins() {
        List<FanZoneAdmin> allAdmins = adminsService.getAllFanZoneAdmins();
        assertThat(allAdmins).hasSize(DB_FAN_COUNT);
    }

    @Test
    public void getByIdFanZoneAdmin() {
        FanZoneAdmin admin = adminsService.getByIdFanZoneAdmin(DB_FAN_ID);
        assertThat(admin).isNotNull();
        assertThat(admin.getUsername()).isEqualTo(DB_FAN_UN);
        assertThat(admin.getPassword()).isEqualTo(DB_FAN_PS);
        assertThat(admin.getAvatar()).isEqualTo(DB_FAN_AV);
        assertThat(admin.isFirstLogin()).isEqualTo(DB_FAN_FL);
        assertThat(admin.getName()).isEqualTo(DB_FAN_NM);
        assertThat(admin.getSurname()).isEqualTo(DB_FAN_SN);
        assertThat(admin.getEmail()).isEqualTo(DB_FAN_EM);
    }

    @Test
    @Transactional
    public void addFanZoneAdmin() {
        FanZoneAdmin admin = new FanZoneAdmin(NEW_FAN_NM,NEW_FAN_SN,NEW_FAN_EM,NEW_FAN_UN,NEW_FAN_PS,NEW_FAN_AV,
                NEW_FAN_FL,NEW_FAN_TEL,NEW_FAN_ADR);
        int count = adminsService.getAllFanZoneAdmins().size();
        FanZoneAdmin temp = adminsService.addFanZoneAdmin(admin);
        assertThat(temp).isNotNull();
        List<FanZoneAdmin> all = adminsService.getAllFanZoneAdmins();
        assertThat(all).hasSize(count+1);
    }

    /**
     * given username already exists in database
     */
  /*  @Test(expected = ValidationException.class)
    @Transactional
    public void noUniqueUsernameAddFanZoneAdmin(){
        FanZoneAdmin admin = new FanZoneAdmin(DB_FAN_NM,DB_FAN_SN,DB_FAN_EM,DB_FAN_UN,DB_FAN_PS,DB_FAN_AV,DB_FAN_FL,
                DB_FAN_TEL,DB_FAN_ADR);
        adminsService.addFanZoneAdmin(admin);
        adminsService.addFanZoneAdmin(admin);
    }*/


    @Test
    public void getAllCaTAdmins() {
        List<CaTAdmin> allAdmins = adminsService.getAllCaTAdmins();
        assertThat(allAdmins).hasSize(DB_CT_COUNT);
    }

    @Test
    public void getByIdCaTAdmins() {
        CaTAdmin admin = adminsService.getByIdCaTAdmins(DB_CT_ID);
        assertThat(admin).isNotNull();
        assertThat(admin.getUsername()).isEqualTo(DB_CT_UN);
        assertThat(admin.getPassword()).isEqualTo(DB_CT_PS);
        assertThat(admin.getAvatar()).isEqualTo(DB_CT_AV);
        assertThat(admin.isFirstLogin()).isEqualTo(DB_CT_FL);
        assertThat(admin.getName()).isEqualTo(DB_CT_NM);
        assertThat(admin.getSurname()).isEqualTo(DB_CT_SN);
        assertThat(admin.getEmail()).isEqualTo(DB_CT_EM);
    }

    @Test
    @Transactional
    public void addCaTAdmin() {
        CaTAdmin admin = new CaTAdmin(NEW_CT_NM,NEW_CT_SN,NEW_CT_EM,NEW_CT_UN,NEW_CT_PS,NEW_CT_AV,NEW_CT_FL,
                NEW_CT_TEL,NEW_CT_ADR,DB_FAC);
        int count = adminsService.getAllCaTAdmins().size();
        CaTAdmin temp = adminsService.addCaTAdmin(admin);
        assertThat(temp).isNotNull();
        List<CaTAdmin> all = adminsService.getAllCaTAdmins();
        assertThat(all).hasSize(count+1);
    }

    /**
     * given username already exists in database
     */
    /*@Test(expected = ValidationException.class)
    @Transactional
    public void noUniqueUsernameAddCaTAdmin(){
        int count = adminsService.getAllCaTAdmins().size();
        List<CaTAdmin> temp = adminsService.getAllCaTAdmins();
        CaTAdmin admin = new CaTAdmin(DB_CT_NM,DB_CT_SN,DB_CT_EM,DB_CT_UN,DB_CT_PS,DB_CT_AV,DB_CT_FL,
                DB_CT_TEL,DB_CT_ADR,DB_FAC);
        adminsService.addCaTAdmin(admin);
    }*/
}