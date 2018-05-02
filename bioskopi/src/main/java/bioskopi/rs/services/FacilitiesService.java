package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;

import java.util.List;

/**
 * Interface that provides service for facilities
 */
public interface FacilitiesService {

    /**
     * @return Collection of all available facilities in database
     */
    List<Facility> findAllFacilities();

    /***
     *
     * @return A facility with given id
     */
    Facility getFacilityById(long id);

    /**
     * @param facility that needs to be added to database
     * @return facility that is added to database
     */
    Facility add(Facility facility);

    /***
     *
     * @param id of facility
     * @return list of projections of given facility
     */
    List<Projection>getRepertoireById(long id);
}
