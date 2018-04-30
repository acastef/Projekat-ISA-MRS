package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Props;

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
}
