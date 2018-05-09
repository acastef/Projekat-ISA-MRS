package bioskopi.rs.repository;

import bioskopi.rs.domain.FanZoneAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicate with database for fan-zone admin data
 */
public interface FanZoneAdminRepository extends JpaRepository<FanZoneAdmin,Long> {

}
