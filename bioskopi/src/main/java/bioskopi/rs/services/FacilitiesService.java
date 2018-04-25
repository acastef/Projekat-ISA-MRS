package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;

import java.util.List;

public interface FacilitiesService {

    /**
     * @return collection of all available facilities in database
     */
    List<Facility> findAllFacilities();
}
