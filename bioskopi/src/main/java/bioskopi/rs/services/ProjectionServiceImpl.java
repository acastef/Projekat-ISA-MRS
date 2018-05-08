package bioskopi.rs.services;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.ProjectionRepository;
import bioskopi.rs.repository.ViewingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of projection service
 */
@Service("projectionService")
public class ProjectionServiceImpl implements ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private ViewingRoomRepository viewingRoomRepository;

    @Override
    public Projection findById(long id) {
       return projectionRepository.getOne(id);
    }

    @Override
    @Transactional
    public Projection save(Projection projection){

        if (projection.getPrice() < 0)
            throw  new ValidationException("Price must be a positive number");
        return projectionRepository.saveAndFlush(projection);
    }

    @Override
    public HashMap<Long, Boolean> getSeatsStatuses(/*viewing room id -->*/Long id, List<Ticket> tickets) {

        List<Seat> seats = projectionRepository.getSeats(id);

        HashMap<Long, Boolean> seatStatuses = new  HashMap<Long, Boolean>();

        for (Seat s:seats) {
            seatStatuses.put(s.getId(), false);
            for (Ticket t: tickets) {
                if (t.getSeat() == s)
                {
                    seatStatuses.put(s.getId(), true);
                    break;
                }
            }

        }

        return seatStatuses;
    }



    @Override
    public List<Ticket> getTickets(Long id) {
        return projectionRepository.getTickets(id);
    }

    @Override
    public Projection add(Projection p) throws ValidationException {
        try{
            Optional<Projection> temp = projectionRepository.findById(p.getId());
            if(temp.isPresent()){
                throw new ValidationException("Projection already exists");
            }
        }catch (NullPointerException e){
            throw new ValidationException("Wrong projections adasdasdsd");
        }

        return projectionRepository.save(p);
    }

    @Override
    public String delete(Long id) {
        try {
            projectionRepository.deleteById(id);
            return "Great Success!";
        }
        catch (Exception e){
            return "Not great success :(";
        }

    }
}
