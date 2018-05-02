package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
        Facility f =  facilityRepository.getOne(id);
        System.out.print("Opis: " + f.getDescription());
        return facilityRepository.getOne(id);
    }
}
