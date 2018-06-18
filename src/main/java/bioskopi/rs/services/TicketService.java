package bioskopi.rs.services;

import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.Ticket;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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

    /***
     *
     * @param id of ticket to delete
     */
    void deleteReservation(long id);

    /***
     *
     * @param id of registered user
     * @return all tickets of registered user
     */
    List<Ticket> getTickets(long id);

//    /**
//     *
//     * @return gets ticket with projekction with earliest date
//     */
//    LocalDate getEarliest(long facId);

    /**
     *
     * @return
     */
    Ticket getByProjectionId(long id);

    /**
     *
     * @param id
     * @return map where key is range of two weeks (exp "2016-04-11, 2016-04-05) and key is number of visists
     *         in that period for facility with given id
     */
    HashMap<String, Integer> getVisitsByWeeks(long id);

    /**
     *
     * @param id
     * @return map where key is month and key is number of visists
     *         in that period for facility with given id
     */
    HashMap<String, Integer> getVisitsByMonths(long id);

    /**
     *
     * @param d1 starting date
     * @param d2 ending date
     * @return Total earnings of facility with given id in the given period of time
     */
    Integer getPricePerPeriod(long id, LocalDateTime d1, LocalDateTime d2);


    /**
     *
     * @param VrId
     * @return list of seats in VR with given id, that are taken
     */
    List<Long> getTakenSeats(long VrId);


    /***
     *
     * @param userId - new owner of ticket
     * @param projId - projection
     * @param seatId - seat
     * @return message
     */
    Object changeOwner(long userId, long projId, long seatId);

}
