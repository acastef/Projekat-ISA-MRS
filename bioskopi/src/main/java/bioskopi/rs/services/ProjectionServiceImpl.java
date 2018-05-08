package bioskopi.rs.services;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of projection service
 */
@Service("projectionService")
public class ProjectionServiceImpl implements ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;

    @Override
    public Projection findById(long id) {
       return projectionRepository.getOne(id);
    }

    @Override
    @Transactional
    public Projection save(Projection projection){

        if (projection.getPrice() < 0)
            throw  new ValidationException("Price must be a positive number");
        return projectionRepository.saveAndFlush(projection);
    }
}
