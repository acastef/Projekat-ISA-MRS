package bioskopi.rs.services;


import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.SegmentEnum;
import bioskopi.rs.domain.ViewingRoom;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.SeatRepository;
import bioskopi.rs.repository.ViewingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.List;

/**
 * Implementation of viewingRoom service
 */
@Service("viewingRoomService")
public class ViewingRoomServiceImpl implements ViewingRoomService {

    @Autowired
    ViewingRoomRepository viewingRoomRepository;

    @Autowired
    SeatRepository seatRepository;

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


    @Transactional
    @Override
    public Boolean closeSegment(Long vrId, SegmentEnum segmentType) {

        try {
            seatRepository.closeSegment(vrId, segmentType);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
