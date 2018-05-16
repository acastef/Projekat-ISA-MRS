package bioskopi.rs.services;

import bioskopi.rs.domain.Ticket;

public interface TicketService {

    /**
     *
     * @param t = Ticket that needs to be added
     * @return added Ticket
     */
    public Ticket add(Ticket t);

    /**
     *
     * @param id of a ticket that needs to be deleted
     * @return String of success of operation
     */
    public String delete(long id);

    /**
     *
     * @param id of a ticket that needs to be added to fast reservation
     * @return String of success of operation
     */
    public Boolean putToFastReservation(long id);


    public Ticket update(Ticket t);

    /**
     *
     * @param id of bla bla bla
     * @return bla x4
     */
    Boolean makeFastReservation(long id);
}
