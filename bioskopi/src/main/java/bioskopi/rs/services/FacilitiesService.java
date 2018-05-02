package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;

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
}
