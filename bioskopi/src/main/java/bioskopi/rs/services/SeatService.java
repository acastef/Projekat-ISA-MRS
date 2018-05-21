package bioskopi.rs.services;

import bioskopi.rs.domain.SegmentEnum;

import java.util.List;

public interface SeatService {

    /**
     *
     * @param listOfIds list of seats ids that need to be changed
     * @param segment type of segment that seats need to be changed to
     * @return true if operation is successful, false otherwise
     */
    Boolean changeSegment(List<Long> listOfIds, SegmentEnum segment);
}
