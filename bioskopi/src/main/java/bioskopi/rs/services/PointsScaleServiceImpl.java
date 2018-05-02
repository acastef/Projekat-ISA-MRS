package bioskopi.rs.services;

import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.repository.PointsScaleRepository;
import bioskopi.rs.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation for points scale service
 */
@Service
public class PointsScaleServiceImpl implements PointsScaleService {

    @Autowired
    private PointsScaleRepository pointsScaleRepository;

    @Override
    public List<PointsScale> getAll() {
        return pointsScaleRepository.findAll();
    }

    @Override
    public PointsScale getById(long id) {
        return pointsScaleRepository.getOne(id);
    }

    @Override
    @Transactional
    public PointsScale save(PointsScale scale) {

        return pointsScaleRepository.saveAndFlush(scale);
    }
}
