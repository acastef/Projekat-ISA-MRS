package bioskopi.rs.services;

import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.ViewingRoom;

import java.util.List;

public interface ViewingRoomService {

    /**
     *
     * @param id of needed viewing room
     * @return viewing room with given id
     */
    public ViewingRoom getById(Long id);

    /**
     *
     * @param id of a viewing room
     * @return list of seats of given viewing room
     */
    public List<Seat> getSeatsById(Long id);
}
