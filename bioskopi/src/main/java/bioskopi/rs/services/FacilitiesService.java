package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.FacilityDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;

import java.util.List;

/**
 * Interface that provides service for facilities
 */
public interface FacilitiesService {

    /**
     * @return Collection of all available facilities in database
     */
    List<FacilityDTO> findAllFacilities();

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

    /***
     *
     * @param type of facility
     * @return list of all facilities by type
     */

    List<Facility>findFacilityByType(String type);

    /**
     *
     * @param facility that needs to be saved
     * @return modified facility
     */
    Facility save(Facility facility);

    /**
     *
     * @param id of facility
     * @return list of fast reservation tickets in facility with given id
     */
    List<Ticket> getFastTickets(long id);


}
