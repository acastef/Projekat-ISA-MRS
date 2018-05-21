package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents projection entity
 */
@Entity
public class Projection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int price;

    @ElementCollection
    @CollectionTable(name = "actors" )
    @Column(name = "actor")
    private Set<String> listOfActors;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String picture;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ViewingRoom viewingRoom;

    @JsonManagedReference(value = "projection")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projection", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    @JsonBackReference
    @ManyToOne(optional = false)
    private Facility facility;

    @JsonManagedReference(value = "projection")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projection", cascade = CascadeType.ALL)
    private Set<Feedback> feedback;



    public Projection() {
    }

    public Projection(String name, LocalDate date, int price, Set<String> listOfActors, String genre,
                      String director, int duration, String picture, String description,
                      ViewingRoom viewingRoom, Set<Ticket> tickets, Set<Feedback> feedback) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.listOfActors = listOfActors;
        this.genre = genre;
        this.director = director;
        this.duration = duration;
        this.picture = picture;
        this.description = description;
        this.viewingRoom = viewingRoom;
        this.tickets = tickets;
        this.feedback = feedback;
    }

    public Projection(String name, LocalDate date, int price, Set<String> listOfActors, String genre,
                      String director, int duration, String picture, String description,
                      ViewingRoom viewingRoom, Set<Ticket> tickets, Facility facility, Set<Feedback> feedback) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.listOfActors = listOfActors;
        this.genre = genre;
        this.director = director;
        this.duration = duration;
        this.picture = picture;
        this.description = description;
        this.viewingRoom = viewingRoom;
        this.tickets = tickets;
        this.facility = facility;
        this.feedback = feedback;
    }

    public Projection(long id, String name, LocalDate date, int price, Set<String> listOfActors, String genre,
                      String director, int duration, String picture, String description,
                      ViewingRoom viewingRoom, Set<Ticket> tickets, Set<Feedback> feedback) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.listOfActors = listOfActors;
        this.genre = genre;
        this.director = director;
        this.duration = duration;
        this.picture = picture;
        this.description = description;
        this.viewingRoom = viewingRoom;
        this.tickets = tickets;
        this.feedback = feedback;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<String> getListOfActors() {
        return listOfActors;
    }

    public void setListOfActors(Set<String> listOfActors) {
        this.listOfActors = listOfActors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ViewingRoom getViewingRoom() {
        return viewingRoom;
    }

    public void setViewingRoom(ViewingRoom viewingRoom) {
        this.viewingRoom = viewingRoom;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Set<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(Set<Feedback> feedback) {
        this.feedback = feedback;
    }
}
