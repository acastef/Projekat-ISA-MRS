package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.*;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service("tickets")
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void add(Ticket ticket)
    {
        try{
            long id = ticket.getProjection().getId();
            Projection p = findById(id);
            List<Ticket> tickets = ticketRepository.getListByProjectionId(p.getId());
            for(Ticket t : tickets) {
                if ((t.getProjection().getId() == ticket.getProjection().getId()) &&
                        (t.getSeat().getId() == ticket.getSeat().getId())) {
                    throw new ValidationException("Seat you chose is already taken!");
                }
            }
            tickets.add(ticket);

            p.setTickets(new HashSet<Ticket>(tickets));

            save(p);
        }catch(LockTimeoutException e){
            throw new ValidationException("Seat you chosen is already taken, refresh page!");
        }
    }


    public Projection findById(long id){
        return projectionRepository.findById(id).get();
    }

    public Projection save(Projection p){
        return projectionRepository.save(p);
    }

    @Override
    public String delete(long id) {
        try {
            ticketRepository.deleteById(id);
            return "Great Success!";
        }
        catch (Exception e){
            return "Not great success :(";
        }

    }

    @Transactional
    @Override
    public Boolean putToFastReservation(long id) {
        try {
            Ticket t = ticketRepository.getOne(id);
            t.setFastReservation(true);
            ticketRepository.save(t);
            return true;
        }
        catch (StaleObjectStateException |OptimisticLockException | ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Tickets are stale, please refresh your page");
        }
    }

    @Transactional
    @Override
    public Ticket update(Ticket t) {

        if (t.getDiscount() <0 || t.getDiscount() > 100)
            throw new ValidationException("Ticket discount can not be less than 0 or more than 100");
        else
            return ticketRepository.saveAndFlush(t);
    }

    @Transactional
    @Override
    public Boolean makeFastReservation(Ticket t) {
        try {
            long id = t.getOwner().getId();
            RegisteredUser u = registeredUserRepository.findById(id).get();
            t.setOwner(u);

            Ticket oldT = ticketRepository.findById(t.getId()).get();
            //oldT.setOwner(u);
            t.setProjection(oldT.getProjection());

            t.setTaken(true);

            ticketRepository.save(t);
            return true;
        }
        catch (StaleObjectStateException |OptimisticLockException | ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Tickets are stale, please refresh your page");
        }
    }

    @Transactional
    @Override
    public void deleteReservation(long id) {
        try{
            Ticket t = ticketRepository.getOne(id);
            ticketRepository.delete(t);
        }catch (ValidationException e){
            throw new ValidationException("Reservation doesn't exists!");
        }
    }

    @Override
    public Ticket getByProjectionId(long projId)
    {
        return ticketRepository.getByProjectionId(projId);
    }

    @Override
    public List<Ticket> getListByProjectionId(long id) {
        return ticketRepository.getListByProjectionId(id);
    }

    @Override
    public List<Ticket> getTickets(long ownerId) {
        List<Ticket> allTickets = ticketRepository.getAllTickets(ownerId);
        return allTickets;
    }

    @Override
    public Ticket getById(long id) {
        return ticketRepository.getOne(id);
    }


    @Override
    public HashMap<String, Integer> getVisitsByWeeks(long id) {

        HashMap<String, Integer> totalVisitsPerWeek = new HashMap<String, Integer>();

        List<Ticket> ticketList = ticketRepository.getTicketsForFacility(id);

        for (Ticket selectedTicket: ticketList) {
            //Projection p = projectionRepository.getOne(selectedTicket.getProjection());
            String week = generateWeek(selectedTicket.getProjection().getDate());

            // Ako ne postoji ubacuje 1 kao vrednost, inace poveacava vrednost za 1
            totalVisitsPerWeek.merge(week, 1, Integer::sum);
        }

        return totalVisitsPerWeek;
    }

    @Override
    public HashMap<String, Integer> getVisitsByMonths(long id) {

        HashMap<String, Integer> totalVisitsPerMonth = new HashMap<String, Integer>();

        List<Ticket> ticketList = ticketRepository.getTicketsForFacility(id);

        for (Ticket selectedTicket: ticketList) {
            //Projection p = projectionRepository.getOne(selectedTicket.getProjection());
            String week = generateMonth(selectedTicket.getProjection().getDate());

            // Ako ne postoji ubacuje 1 kao vrednost, inace poveacava vrednost za 1
            totalVisitsPerMonth.merge(week, 1, Integer::sum);
        }

        return totalVisitsPerMonth;
    }

    @Override
    public Integer getPricePerPeriod(long id, LocalDateTime d1, LocalDateTime d2) {

        Integer totalEarnings = 0;
        List<Ticket> ticketList = ticketRepository.getTicketsForFacility(id);
        for (Ticket selectedTicket: ticketList) {
            LocalDateTime dateOfTicket = selectedTicket.getProjection().getDate();

            // equal stoji da obuhvati i taj dan koji je odabran
            if ( (dateOfTicket.isBefore(d2) || dateOfTicket.equals(d2) ) &&
                    (dateOfTicket.isAfter(d1) || dateOfTicket.equals(d1) ) )
                totalEarnings += selectedTicket.getProjection().getPrice();
        }

        return totalEarnings;
    }

    @Override
    public List<Long> getTakenSeats(long VrId) {
        return ticketRepository.getTakenSeats(VrId);
    }

    @Transactional
    @Override
    public Object changeOwner(long userId, long projId, long seatId) {
        ticketRepository.changeTicketOwner(userId, projId, seatId);
        return "Invitation accepted";
    }

    private String generateMonth(LocalDateTime d) {

        String retVal = d.getYear() + "-" +  d.getMonth();
        return retVal;
    }

    private String generateWeek(LocalDateTime d) {
        LocalDateTime previous = d.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime next = d.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        String retVal = previous.toString() + "," + next.toString();
        return retVal;
    }



}