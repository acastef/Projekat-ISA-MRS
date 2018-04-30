package bioskopi.rs.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Represents theater entity
 */
@Entity
@DiscriminatorValue("theater")
public class Theater extends Facility {

    public Theater() {
    }

    public Theater(long id, String name, String address, String description, Set<ViewingRoom> viewingRooms,
                   Set<Projection> projections, PointsScale pointsScales, Set<Ticket> tickets/*, Set<Props> props*/) {
        super(id, name, address, description, viewingRooms, projections, pointsScales, tickets);
    }
}
