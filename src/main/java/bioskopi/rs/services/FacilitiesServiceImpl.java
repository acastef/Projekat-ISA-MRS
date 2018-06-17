package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.FacilityDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.ViewingRoom;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bioskopi.rs.validators.FacilitiesValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    @Autowired
    private ViewingRoomRepository viewingRoomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

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
        Facility temp = facilityRepository.saveAndFlush(facility);
        for (ViewingRoom vm:
             temp.getViewingRooms()) {
            seatRepository.saveAll(vm.getSeats());
        }
        return temp;
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
        Facility temp = facilityRepository.saveAndFlush(facility);

        temp.setViewingRooms(new HashSet<>(viewingRoomRepository.saveAll(temp.getViewingRooms())));
        for (ViewingRoom vm :
                temp.getViewingRooms()) {
            seatRepository.saveAll(vm.getSeats());
        }
        return temp;

    }

    @Override
    public List<Ticket> getFastTickets(long id) {
        List<Ticket> temp = ticketRepository.getFastTickets(id);
        return temp;
    }

    @Override
    public HashMap<Long, Double> getProjectionsAverageScore(long id) {

        HashMap<Long, Double> avgScores = new HashMap<>();

        List<Projection> projections = facilityRepository.findRepertoireById(id);

        for (Projection p: projections) {
            Double avg = feedbackRepository.getAverageScore(p.getId());
            avgScores.put(p.getId(), avg);
        }

        return avgScores;
    }

    @Override
    public Double getAverageScore(long id) {
        return feedbackRepository.getFacilityAverageScore(id);
    }


}
