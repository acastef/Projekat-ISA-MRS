package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@DiscriminatorValue("registered")
public class RegisteredUser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = true)
    private int points;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "registeredUser", cascade = CascadeType.ALL)
    private Set<PropsReservation> propsReservations;

    @JsonManagedReference(value = "registerUser")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY)
    private List<Friendship> friends;

    public RegisteredUser() {

    }

    public RegisteredUser(long id, String name, String surname, String email, String username, String password,
                          String avatar, boolean firstLogin, String telephone, String address) {
        super(id, name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.propsReservations = new HashSet<PropsReservation>();
        this.tickets = new HashSet<Ticket>();
        this.friends = new ArrayList<Friendship>();
        this.points = 0;
    }

    public RegisteredUser(long id, String name, String surname, String email, String username, String password,
                          String avatar, boolean firstLogin, String telephone, String address,
                          Set<PropsReservation> propsReservations, Set<Ticket> tickets, List<Friendship> friends) {
        super(id, name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.propsReservations = propsReservations;
        this.tickets = tickets;
        this.friends = friends;
        this.points = 0;
    }

    public RegisteredUser(String name, String surname, String email, String username, String password, String avatar,
                          boolean firstLogin, String telephone, String address, Set<PropsReservation> propsReservations,
                          Set<Ticket> tickets, List<Friendship> friends) {
        super(name, surname, email, username, password, avatar, firstLogin, telephone, address);
        this.propsReservations = propsReservations;
        this.tickets = tickets;
        this.friends = friends;
        this.points = 0;
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

    public List<Friendship> getFriends() {
        return friends;
    }

    public void setFriends(List<Friendship> friends) {
        this.friends = friends;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
