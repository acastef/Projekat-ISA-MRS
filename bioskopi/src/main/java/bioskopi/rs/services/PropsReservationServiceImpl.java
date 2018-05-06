package bioskopi.rs.services;

import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.repository.PropsReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of props reservation service
 */
@Service("propsReservationService")
public class PropsReservationServiceImpl implements PropsReservationService {

    @Autowired
    private PropsReservationRepository propsReservationRepository;

    @Override
    @Transactional
    public PropsReservation add(PropsReservation propsReservation) {
        return propsReservationRepository.save(propsReservation);

    }

    @Override
    public PropsReservation getByUserIdAndPropsId(long userId, long propsId) {
        return propsReservationRepository.findByUserAndProps(userId, propsId).orElse(
                new PropsReservation(-1, null, null, -1));
    }
}
