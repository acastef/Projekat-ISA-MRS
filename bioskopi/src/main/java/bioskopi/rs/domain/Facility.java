package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Represents Facility entity
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
//@JsonIgnoreProperties("inspection")
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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility", cascade = CascadeType.ALL)
    private Set<ViewingRoom> viewingRooms;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility", cascade = CascadeType.ALL)
    private Set<Projection> projections;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "facility", cascade = CascadeType.ALL)
    private PointsScale pointsScales;

    //@JsonManagedReference(value = "facility")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;


    public Facility() {
    }

    public Facility(long id, String name, String address, String description, Set<ViewingRoom> viewingRooms,
                    Set<Projection> projections, PointsScale pointsScales, Set<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.viewingRooms = viewingRooms;
        this.projections = projections;
        this.pointsScales = pointsScales;
        this.tickets = tickets;
    }

    public Facility( String name, String address, String description, Set<ViewingRoom> viewingRooms,
                    Set<Projection> projections, PointsScale pointsScales, Set<Ticket> tickets) {
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

    public PointsScale getPointsScales() {
        return pointsScales;
    }

    public void setPointsScales(PointsScale pointsScales) {
        this.pointsScales = pointsScales;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

}

