package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Feedback;
import bioskopi.rs.domain.Ticket;
import bioskopi.rs.domain.ViewingRoom;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ProjectionDTO implements Serializable {

    private long id;

    private String name;

    private String date;

    private int price;

    private Set<String> listOfActors;

    private String genre;

    private String director;

    private int duration;

    private String picture;

    private String description;

    private ViewingRoom viewingRoom;

    private Facility facility;

    public ProjectionDTO()
    {

    }

    public ProjectionDTO(String date, int price, Set<String> listOfActors, String genre, String director, int duration,
                         String picture, String description, ViewingRoom viewingRoom, Facility facility) {



        this.date =  date;
        this.price = price;
        this.listOfActors = listOfActors;
        this.genre = genre;
        this.director = director;
        this.duration = duration;
        this.picture = picture;
        this.description = description;
        this.viewingRoom = viewingRoom;
        this.facility = facility;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
