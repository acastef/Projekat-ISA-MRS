package bioskopi.rs.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("cinema")
public class Cinema extends Facility {

    public Cinema() {
    }
}
