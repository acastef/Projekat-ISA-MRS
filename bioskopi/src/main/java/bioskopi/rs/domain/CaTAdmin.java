package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@DiscriminatorValue("catAdmin")
public class CaTAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn
    private Facility facility;

    public CaTAdmin() {
    }

    public CaTAdmin(long id, String name, String surname, String email, String username, String password, String avatar,
                    boolean firstLogin, String telephone, String address, Facility facility) {
        super(id, name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.facility = facility;
    }

    public CaTAdmin(String name, String surname, String email, String username, String password, String avatar,
                    boolean firstLogin, String telephone, String address, Facility facility) {
        super(name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.facility = facility;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
