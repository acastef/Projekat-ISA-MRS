package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "viewing_rooms")
public class ViewingRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "viewingRoom")
    private Set<Seat> seats;

    public ViewingRoom() {
    }

    public ViewingRoom(long id, String name ,Set<Seat> seats) {
        this.id = id;
        this.name = name;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }
}
