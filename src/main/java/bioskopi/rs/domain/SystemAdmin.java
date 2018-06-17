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

    public SystemAdmin(long id, String name, String surname, String email, String username, String password,
                       String avatar, boolean firstLogin, String telephone, String address) {
        super(id, name, surname, email, username, password, avatar, firstLogin, telephone, address);
    }

    public SystemAdmin(String name, String surname, String email, String username, String password, String avatar,
                       boolean firstLogin, String telephone, String address) {
        super(name, surname, email, username, password, avatar, firstLogin, telephone, address);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
