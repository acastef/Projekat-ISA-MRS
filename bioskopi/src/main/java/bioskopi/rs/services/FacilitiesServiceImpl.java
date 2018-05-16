package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.FacilityDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bioskopi.rs.validators.FacilitiesValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of facility service
 */
@Service("facilityService")
public class FacilitiesServiceImpl implements FacilitiesService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<FacilityDTO> findAllFacilities() {

        List<Facility> listOfFac  = facilityRepository.findAll();
        List<FacilityDTO> listOfFacDTOs =  new ArrayList<FacilityDTO>();


        for (Facility f: listOfFac) {
            List<String> type = facilityRepository.getType(f.getId());

            FacilityDTO fDTO = new FacilityDTO(f);
            fDTO.setType(type.get(0));

            listOfFacDTOs.add(fDTO);
        }

        return listOfFacDTOs;
    }

    @Override
    public List<Facility> getAll() {
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

    @Override
    @Transactional
    public Facility save(Facility facility) {
        return  facilityRepository.saveAndFlush(facility);
    }

    @Override
    public List<Ticket> getFastTickets(long id) {
        List<Ticket> temp = ticketRepository.getFastTickets(id);
        return temp;
    }


}
