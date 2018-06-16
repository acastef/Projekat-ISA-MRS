package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.ReservedPropsDTO;
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

    /**
     * @param userId given user id
     * @param propsId given props id
     * @return props reservation with given user id and props id
     */
    PropsReservation getByUserIdAndPropsId(long userId, long propsId);

    /**
     * @param userId give user id
     * @return props reservation with given user id
     */
    List<ReservedPropsDTO> getByUserId(long userId);
}
