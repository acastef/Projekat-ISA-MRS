package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;

import java.util.List;

/**
 * Interface that provides service for facilities
 */
public interface FacilitiesService {

    /**
     * @return collection of all available facilities in database
     */
    List<Facility> findAllFacilities();

    /**
     * @param facility that needs to be added to database
     * @return facility that is added to database
     */
    Facility add(Facility facility);
}
