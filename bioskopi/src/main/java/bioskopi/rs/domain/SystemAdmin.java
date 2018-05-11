package bioskopi.rs.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@DiscriminatorValue("sysAdmin")
public class SystemAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;


    public SystemAdmin() {
        this.setFirstLogin(true);
    }

    public SystemAdmin(String name, String surname, String email, String username, String password,
                       String avatar) {
        super(name, surname, email, username, password, avatar, true);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
