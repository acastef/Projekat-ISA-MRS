package bioskopi.rs.services;


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
}
