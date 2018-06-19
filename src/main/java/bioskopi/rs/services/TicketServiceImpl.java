package bioskopi.rs.services;

import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.Seat;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.ProjectionRepository;
import bioskopi.rs.repository.TicketRepository;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("tickets")
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Override
    @Transactional
    public Ticket add(Ticket ticket)
    {
        return ticketRepository.saveAndFlush(ticket);
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
    public Boolean makeFastReservation(long id) {
        try {
            ticketRepository.makeFastReservation(id);
            return true;
        }
        catch (StaleObjectStateException |OptimisticLockException | ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Tickets are stale, please refresh your page");
        }
    }

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
    public List<Ticket> getTickets(long ownerId) {
        List<Ticket> allTickets = ticketRepository.getAllTickets(ownerId);
        return allTickets;
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