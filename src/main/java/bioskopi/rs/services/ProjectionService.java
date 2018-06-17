package bioskopi.rs.services;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;

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
    HashMap<Long, Boolean> getSeatsStatuses(Long id, List<Ticket> tickets);

    /**
     *
     * @param id of projection
     * @return list of ticket for projection with given id
     */
    List<Ticket> getTickets(Long id);

    /**
     *
     * @param p a projection that needs to be added
     * @return a projection that is added
     * @throws ValidationException
     */
    Projection add(Projection p) throws ValidationException;

    /**
     *
     * @param id of projection that needs to be deleted
     * @return message that show if operation was successful
     */
    String delete(Long id);


    /**
     *
     * @param id
     * @return
     */
    List<Projection> findByUserId(long id);

    /**
     *
     * @return
     */
    Projection getEarliest(long id);
}
