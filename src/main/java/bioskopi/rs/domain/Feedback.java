package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private RegisteredUser registeredUser;

    @JsonBackReference(value = "facility")
    @ManyToOne
    private Facility facility;

    @JsonBackReference(value = "projection")
    @ManyToOne
    private Projection projection;


    public Feedback() {
    }

    public Feedback(int score, String description, RegisteredUser registeredUser, Projection projection, Facility facility) {
        this.score = score;
        this.description = description;
        this.registeredUser = registeredUser;
        this.facility = facility;
        this.projection = projection;
    }

    public Feedback(long id, int score, String description, RegisteredUser registeredUser, Projection projection, Facility facility) {
        this.id = id;
        this.score = score;
        this.description = description;
        this.registeredUser = registeredUser;
        this.facility = facility;
        this.projection = projection;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }
}
