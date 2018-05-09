package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represent the thematic props which can be reserved
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Props implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String description;


    @Column(nullable = true)
    private String image;

    @JsonBackReference
    @ManyToOne(optional = false)
    private Facility facility;

    @Column(nullable = false)
    private boolean reserved;

    @Column(nullable = false)
    private boolean active;

    public Props() {
        this.reserved = false;
        this.active = true;
    }

    public Props(long id, String description, String image, Facility facility) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.facility = facility;
        this.reserved = false;
        this.active = true;
    }

    public Props( String description, String image, Facility facility) {
        this.description = description;
        this.image = image;
        this.facility = facility;
        this.reserved = false;
        this.active = true;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Props{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", facility=" + facility +
                '}';
    }
}
