package bioskopi.rs.services;

import bioskopi.rs.domain.SegmentEnum;
import bioskopi.rs.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("seatService")
public class SeatServiceImpl implements SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Transactional
    @Override
    public Boolean changeSegment(List<Long> listOfIds, SegmentEnum segment) {

        for (Long seatId: listOfIds) {

            try{
                seatRepository.changeSegment(seatId, segment);
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return true;
    }
}
