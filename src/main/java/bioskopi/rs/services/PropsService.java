package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.PropsReservation;

import java.util.List;

/**
 * Interface that provides service for props
 */
public interface PropsService {

    /**
     * @param description of targeted props
     * @return props with gi description
     */
    PropsDTO findByDescription(String description);

    /**
     * @return collection of all available props in database
     */
    List<PropsDTO> findAllProps();

    /**
     * @param id of targeted props
     * @return props with given id
     */
    PropsDTO findById(long id);


    /**
     * @param props that needs to be added
     * @return added props in database
     */
    Props add(Props props);


    /**
     * @param props that needs to be deleted from database
     */
    void delete(Props props);
}
