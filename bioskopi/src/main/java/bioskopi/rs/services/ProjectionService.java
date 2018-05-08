package bioskopi.rs.services;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;

import java.util.HashMap;
import java.util.List;

public interface ProjectionService {

    /**
     *
     * @param id of needed projection
     * @return projection with given id
     */
    Projection findById(long id);


    /**
     *
     * @param projection that needs to be saved
     * @return modified projection
     */
    Projection save(Projection projection);

    /**
     *
     * @param id of projection
     * @return hash map where key is seat's id and value is bool value that shows if seat is taken(true = taken)
     */
    HashMap<Integer, Boolean> getSeatsStatuses(Long id);

    /**
     *
     * @param id of projection
     * @return list of ticket for projection with given id
     */
    List<Ticket> getTickets(Long id);
}
