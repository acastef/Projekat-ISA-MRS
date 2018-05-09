package bioskopi.rs.services;

import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.PropsRepository;
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

    @Autowired
    private PropsRepository propsRepository;

    @Override
    @Transactional
    public PropsReservation add(PropsReservation propsReservation) {
        Optional<Props> temp = propsRepository.findById(propsReservation.getProps().getId());
        if(temp.isPresent()){
            Props props = temp.get();
            if(!props.isActive()){
                throw new ValidationException("Props no longer exist.");
            }
            props.setReserved(true);
            propsRepository.save(props);
            return propsReservationRepository.save(propsReservation);
        }else{
            throw new ValidationException("Props does not exist");
        }


    }

    @Override
    public PropsReservation getByUserIdAndPropsId(long userId, long propsId) {
        return propsReservationRepository.findByUserAndProps(userId, propsId).orElse(
                new PropsReservation(-1, null, null, -1));
    }
}
