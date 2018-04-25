package bioskopi.rs.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("theater")
public class Theater extends Facility {

    public Theater() {
    }

}
