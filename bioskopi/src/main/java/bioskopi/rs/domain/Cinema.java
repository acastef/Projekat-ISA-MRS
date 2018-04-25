package bioskopi.rs.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Represents cinema entity
 */
@Entity
@DiscriminatorValue("cinema")
public class Cinema extends Facility {

    public Cinema() {
    }
}
