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

    public FanZoneAdmin(long id, String name, String surname, String email, String username, String password,
                        String avatar, boolean firstLogin, String telephone, String address) {
        super(id, name, surname, email, username, password, avatar, firstLogin, telephone, address);
    }

    public FanZoneAdmin(String name, String surname, String email, String username, String password, String avatar,
                        boolean firstLogin, String telephone, String address) {
        super(name, surname, email, username, password, avatar, firstLogin, telephone, address);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
