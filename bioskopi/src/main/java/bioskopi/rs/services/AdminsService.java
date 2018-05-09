package bioskopi.rs.services;

import bioskopi.rs.domain.CaTAdmin;
import bioskopi.rs.domain.FanZoneAdmin;

import javax.smartcardio.CardTerminal;
import java.util.List;

/**
 * Interface that provides service for fan-zone admin
 */
public interface AdminsService {

    /**
     * @return collection of all available fan-zone admins in database
     */
    List<FanZoneAdmin> getAllFanZoneAdmins();

    /**
     * @param id of targeted fan-zone admin
     * @return fan-zone admin with given id
     */
    FanZoneAdmin getByIdFanZoneAdmin(long id);

    /**
     * @param admin that need to be added
     * @return added admin in database
     */
    FanZoneAdmin addFanZoneAdmin(FanZoneAdmin admin);

    /**
     * @return collection of all available cinema or theater admins in database
     */
    List<CaTAdmin> getAllCaTAdmins();

    /**
     * @param id of targeted cinema or theater admin
     * @return cinema or theater admin in database
     */
    CaTAdmin getByIdCaTAdmins(long id);

    /**
     * @param admin that need to be added
     * @return added admin in database
     */
    CaTAdmin addCaTAdmin(CaTAdmin admin);

}
