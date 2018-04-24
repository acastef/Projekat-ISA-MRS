package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility")
    private Set<ViewingRoom> viewingRooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility")
    private Set<Projection> projections;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility")
    private Set<PointsScale> pointsScales;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility")
    private Set<Ticket> tickets;

    public Facility() {
    }

    public Facility(long id, String name, String address, String description, Set<ViewingRoom> viewingRooms,
                    Set<Projection> projections, Set<PointsScale> pointsScales, Set<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.viewingRooms = viewingRooms;
        this.projections = projections;
        this.pointsScales = pointsScales;
        this.tickets = tickets;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<ViewingRoom> getViewingRooms() {
        return viewingRooms;
    }

    public void setViewingRooms(Set<ViewingRoom> viewingRooms) {
        this.viewingRooms = viewingRooms;
    }

    public Set<Projection> getProjections() {
        return projections;
    }

    public void setProjections(Set<Projection> projections) {
        this.projections = projections;
    }

    public Set<PointsScale> getPointsScales() {
        return pointsScales;
    }

    public void setPointsScales(Set<PointsScale> pointsScales) {
        this.pointsScales = pointsScales;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
