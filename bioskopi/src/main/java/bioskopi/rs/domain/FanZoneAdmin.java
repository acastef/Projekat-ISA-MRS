package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

public class FanZoneAdmin extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean firstLogIn;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Person person;

    public FanZoneAdmin() {
    }

    public FanZoneAdmin(String username, String password, String avatar, boolean firstLogIn, Person person) {
        super(username, password, avatar);
        this.firstLogIn = firstLogIn;
        this.person = person;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFirstLogIn() {
        return firstLogIn;
    }

    public void setFirstLogIn(boolean firstLogIn) {
        this.firstLogIn = firstLogIn;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
