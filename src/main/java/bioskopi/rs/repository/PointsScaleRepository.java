package bioskopi.rs.repository;

import bioskopi.rs.domain.PointsScale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface that communicate with database for points scale
 */

public interface PointsScaleRepository extends JpaRepository<PointsScale,Long> {




}
