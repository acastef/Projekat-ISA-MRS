package bioskopi.rs.services;


import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.ViewingRoom;
import bioskopi.rs.repository.ViewingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of viewingRoom service
 */
@Service("viewingRoomService")
public class ViewingRoomServiceImpl implements ViewingRoomService {

    @Autowired
    ViewingRoomRepository viewingRoomRepository;

    @Override
    public ViewingRoom getById(Long id) {
        return viewingRoomRepository.getOne(id);
    }

    @Override
    public List<Seat> getSeatsById(Long id) {
        return viewingRoomRepository.getSeats(id);
    }

    @Override
    public List<ViewingRoom> getAll() {
        return viewingRoomRepository.findAll();
    }

    @Override
    public List<ViewingRoom> getAllForFacility(Long facilityId) {
        return viewingRoomRepository.getAllForFac(facilityId);
    }

    @Override
    public Facility getFacility(Long id) {
        return viewingRoomRepository.getFacility(id);
    }
}
