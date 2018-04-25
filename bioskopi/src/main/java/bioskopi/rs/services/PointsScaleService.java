package bioskopi.rs.services;

import bioskopi.rs.domain.PointsScale;

import java.util.List;

public interface PointsScaleService {

    List<PointsScale> getAll();
    PointsScale getById(long id);
}
