package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bioskopi.rs.validators.FacilitiesValidator;
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

    @Override
    @Transactional
    public Facility add(Facility facility) throws ValidationException {
        FacilitiesValidator.checkFacility(facility);
        List<Facility> facilities = facilityRepository.findAll();
        for (Facility fac :
                facilities) {
            if(fac.getName().compareTo(facility.getName()) == 0){
              throw new ValidationException("Facility with given name already exists");
            }
        }
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
