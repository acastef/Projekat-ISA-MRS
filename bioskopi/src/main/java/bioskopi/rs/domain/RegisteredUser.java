package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RegisteredUser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private long id;


    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "registeredUser", cascade = CascadeType.ALL)
    private Set<PropsReservation> propsReservations;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Person person;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private Set<Ticket> tickets;

    public RegisteredUser() {

    }

    public RegisteredUser(String username, String password, String avatar,
                          long id, Set<PropsReservation> propsReservations, Person person) {
        super(username, password, avatar);
        this.id = id;
        this.propsReservations = propsReservations;
        this.person = person;
    }

    public RegisteredUser(String username, String password, String avatar,
                          Set<PropsReservation> propsReservations, Person person) {
        super(username, password, avatar);
        this.propsReservations = propsReservations;
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<PropsReservation> getPropsReservations() {
        return propsReservations;
    }

    public void setPropsReservations(Set<PropsReservation> propsReservations) {
        this.propsReservations = propsReservations;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
