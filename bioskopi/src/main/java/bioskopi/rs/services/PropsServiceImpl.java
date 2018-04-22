package bioskopi.rs.services;

import bioskopi.rs.domain.Props;
import bioskopi.rs.repository.PropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("propsService")
public class PropsServiceImpl implements PropsService {

    /*
    * Implementation of PropsService
    */

    @Autowired
    private PropsRepository propsRepository;


    @Override
    public Props findByDescription(String description) {
        return propsRepository.findByDescription(description);
    }

    @Override
    public List<Props> findAllProps() {
        return propsRepository.findAll();
    }
}
