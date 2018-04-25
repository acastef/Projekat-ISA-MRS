package bioskopi.rs.services;

import bioskopi.rs.domain.PointsScale;
import bioskopi.rs.repository.PointsScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
