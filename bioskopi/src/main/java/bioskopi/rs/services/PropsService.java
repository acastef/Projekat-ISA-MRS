package bioskopi.rs.services;

import bioskopi.rs.domain.Props;

import java.util.List;

/**
 * Interface that offers service for props
 */
public interface PropsService {

    /**
     * @param description of targeted props
     * @return props with gi description
     */
    Props findByDescription(String description);

    /**
     * @return collection of all available props in database
     */
    List<Props> findAllProps();
}
