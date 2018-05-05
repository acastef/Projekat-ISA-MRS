package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Console;
import java.util.List;

/**
 * Implementation of facility service
 */
@Service("facilityService")
public class FacilitiesServiceImpl implements FacilitiesService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public List<Facility> findAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    public Facility getFacilityById(long id) {
        return facilityRepository.getOne(id);
    }
    @Transactional
    public Facility add(Facility facility) {
        return facilityRepository.saveAndFlush(facility);
    }

    @Override
    public List<Projection> getRepertoireById(long id) {
        return facilityRepository.findRepertoireById(id);
    }

    @Override
    public List<Facility> findFacilityByType(String type){
        return facilityRepository.findFacilityByType(type);
    }


}
