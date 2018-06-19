package bioskopi.rs.services;

import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.domain.UserCategory;
import bioskopi.rs.domain.util.ValidationException;

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


    /**
     * @param scale that need to be saved
     * @return saved points scale
     */
    PointsScale save(PointsScale scale) throws ValidationException;

    /***
     *
     * @param id - facility
     * @return list of categories for given facility
     */
    List<UserCategory> getFromFacility(long id);
}
