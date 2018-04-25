package bioskopi.rs.repository;

import bioskopi.rs.domain.PointsScale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicate with database for points scale
 */
public interface PointsScaleRepository extends JpaRepository<PointsScale,Long> {


}
