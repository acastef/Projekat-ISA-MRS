package bioskopi.rs.services;

import bioskopi.rs.domain.Projection;

public interface ProjectionService {

    /**
     *
     * @param id of needed projection
     * @return projection with given id
     */
    Projection findById(long id);


    /**
     *
     * @param projection that needs to be saved
     * @return modified projection
     */
    Projection save(Projection projection);
}
