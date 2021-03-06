package bioskopi.rs.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Represents cinema entity
 */
@Entity
@DiscriminatorValue("cinema")
public class Cinema extends Facility {

    public Cinema() {
    }

    public Cinema(long id, String name, String address, String description, Set<ViewingRoom> viewingRooms,
                  Set<Projection> projections, PointsScale pointsScales, Set<Ticket> tickets, Set<Feedback> feedbacks) {
        super(id, name, address, description, viewingRooms, projections, pointsScales, tickets, feedbacks);
    }

    public Cinema(String name, String address, String description, Set<ViewingRoom> viewingRooms,
                  Set<Projection> projections, PointsScale pointsScales, Set<Ticket> tickets, Set<Feedback> feedbacks) {
        super(name, address, description, viewingRooms, projections, pointsScales, tickets, feedbacks);
    }
}
