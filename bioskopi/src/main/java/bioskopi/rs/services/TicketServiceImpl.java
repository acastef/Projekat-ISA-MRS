package bioskopi.rs.services;

import bioskopi.rs.domain.Ticket;
import bioskopi.rs.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tickets")
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;

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

    @Override
    public Boolean putToFastReservation(long id) {
        try {
            ticketRepository.changeTicket(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Ticket update(Ticket t) {
        return ticketRepository.saveAndFlush(t);
    }

}
