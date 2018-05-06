package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Represents ticket entity
 */
@Entity
public class Ticket {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SeatStatus seatStatus;

    @Column(nullable = false)
    private boolean taken;

    @JsonBackReference(value = "registerUser")
    @ManyToOne(optional = false)
    private RegisteredUser owner;

    @Column(nullable = false)
    private Seat seat;

    @JsonBackReference(value = "projection")
    @ManyToOne(optional = false)
    private Projection projection;

    @JsonBackReference(value = "facility")
    @ManyToOne(optional = false)
    private Facility facility;


    public Ticket() {
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
}
