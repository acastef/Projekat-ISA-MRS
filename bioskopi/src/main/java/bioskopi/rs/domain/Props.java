package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represent the thematic props which can be reserved
 */
@Entity
public class Props implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = true)
    private String image;

    @JsonBackReference
    @ManyToOne(optional = false)
    private Facility facility;


    public Props() {
        this.quantity = 0;
    }

    public Props(long id, String description, long quantity, String image, Facility facility) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.facility = facility;
    }

    public Props( String description, long quantity, String image, Facility facility) {
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.facility = facility;
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

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "Props{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", facility=" + facility +
                '}';
    }
}
