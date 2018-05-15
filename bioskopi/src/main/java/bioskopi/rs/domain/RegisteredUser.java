package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;


@Entity
@DiscriminatorValue("registered")
public class RegisteredUser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "registeredUser", cascade = CascadeType.ALL)
    private Set<PropsReservation> propsReservations;

    //@JsonManagedReference(value = "registerUser")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="friends", joinColumns = {@JoinColumn(name="first")},
                inverseJoinColumns = {@JoinColumn(name="second")})
    private Set<RegisteredUser> friends;

    public RegisteredUser() {

    }

    public RegisteredUser(long id, String name, String surname, String email, String username, String password,
                          String avatar, boolean firstLogin, String telephone, String address,
                          Set<PropsReservation> propsReservations, Set<Ticket> tickets, Set<RegisteredUser> friends) {
        super(id, name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.propsReservations = propsReservations;
        this.tickets = tickets;
        this.friends = friends;
    }

    public RegisteredUser(String name, String surname, String email, String username, String password, String avatar,
                          boolean firstLogin, String telephone, String address, Set<PropsReservation> propsReservations,
                          Set<Ticket> tickets, Set<RegisteredUser> friends) {
        super(name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.propsReservations = propsReservations;
        this.tickets = tickets;
        this.friends = friends;
    }

    public Set<PropsReservation> getPropsReservations() {
        return propsReservations;
    }

    public void setPropsReservations(Set<PropsReservation> propsReservations) {
        this.propsReservations = propsReservations;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<RegisteredUser> getFriends() {
        return friends;
    }

    public void setFriends(Set<RegisteredUser> friends) {
        this.friends = friends;
    }
}
