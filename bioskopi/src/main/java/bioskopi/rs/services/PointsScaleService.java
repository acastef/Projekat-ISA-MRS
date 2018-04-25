package bioskopi.rs.services;

import bioskopi.rs.domain.PointsScale;

import java.util.List;

/**
 * Interface that provides service for points scale
 */
public interface PointsScaleService {

    /**
     * @return all available points scales in database
     */
    List<PointsScale> getAll();

    /**
     * @param id of points scale
     * @return points scale with given id
     */
    PointsScale getById(long id);
}
