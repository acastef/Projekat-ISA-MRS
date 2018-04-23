package bioskopi.rs.domain;

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
    private long quantity = 0;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    private String location;

    public Props() {
    }

    public Props(long id, String description, long quantity, String image, String location) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.image = image;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Props{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                '}';
    }
}
