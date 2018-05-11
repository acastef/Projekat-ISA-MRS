package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@DiscriminatorValue("fanAdmin")
public class FanZoneAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    public FanZoneAdmin() {
    }

    public FanZoneAdmin(String name, String surname, String email, String username,
                        String password, String avatar, boolean fl) {
        super(name, surname, email, username, password, avatar, fl);
    }

    public FanZoneAdmin(long id, String name, String surname, String email, String username,
                        String password, String avatar, boolean fl) {
        super(id, name, surname, email, username, password, avatar, fl);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
