package bioskopi.rs.services;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.SegmentEnum;
import bioskopi.rs.domain.ViewingRoom;

import java.util.List;

public interface ViewingRoomService {

    /**
     *
     * @param id of needed viewing room
     * @return viewing room with given id
     */
    ViewingRoom getById(Long id);

    /**
     *
     * @param id of a viewing room
     * @return list of seats of given viewing room
     */
    List<Seat> getSeatsById(Long id);

    /**
     * @return list of all seats in viewing rooms
     */
    List<ViewingRoom> getAll();

    /**
     *
     * @param id of facility
     * @return list of viewing rooms in facility with given id
     */
    List<ViewingRoom> getAllForFacility(Long id);

    /**
     *
     * @param id of viewing room
     * @return a facility that has viewing room with given id
     */
    public Facility getFacility(Long id);

    /**
     *
     * @param VrId id of viewing room
     * @param segmentType type of segment that needs to be closed in viewing room with given id
     */
    public Boolean closeSegment(Long VrId, SegmentEnum segmentType);

    /**
     *
     * @param VrId id of a viewing room
     * @return list of all taken seats in that viewing room
     */
    public List<Long> getTakenSeats (long VrId);
}
