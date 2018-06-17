package bioskopi.rs.services;

import bioskopi.rs.domain.PropsReservation;

import java.util.List;

/**
 * Interface that provides service for props reservation
 */
public interface PropsReservationService {

    /**
     * @param propsReservation that need to be added to database
     * @return added props reservation to database
     */
    PropsReservation add(PropsReservation propsReservation);

    PropsReservation getByUserIdAndPropsId(long userId, long propsId);
}
