package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents ticket entity
 */
@Entity
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SeatStatus seatStatus;

    @Column(nullable = false)
    private int discount;

    @Column(nullable = false)
    private boolean taken;

    @JsonBackReference(value = "registerUser")
    @ManyToOne(optional = false)
    private RegisteredUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Seat seat;

    @JsonBackReference(value = "projection")
    @ManyToOne(optional = false)
    private Projection projection;

    @JsonBackReference(value = "facility")
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Facility facility;

    @Column(nullable = false)
    private boolean fastReservation;

    @Version
    @Column(nullable = false)
    private long version;



    public Ticket() {
    }

    public Ticket(long id, SeatStatus seatStatus, boolean taken, RegisteredUser owner, Seat seat, Projection projection, Facility facility) {
        this.id = id;
        this.discount = 0;
        this.seatStatus = seatStatus;
        this.taken = taken;
        this.owner = owner;
        this.seat = seat;
        this.projection = projection;
        this.facility = facility;
        this.fastReservation = false;
    }

    public Ticket(int discount, long id, SeatStatus seatStatus, boolean taken, RegisteredUser owner, Seat seat, Projection projection, Facility facility) {
        this.id = id;
        this.discount = discount;
        this.seatStatus = seatStatus;
        this.taken = taken;
        this.owner = owner;
        this.seat = seat;
        this.projection = projection;
        this.facility = facility;
        this.fastReservation = false;
    }

    public Ticket(SeatStatus seatStatus, boolean taken, RegisteredUser owner, Seat seat, Projection projection,
                  Facility facility, Long version) {
        this.discount = 0;
        this.seatStatus = seatStatus;
        this.taken = taken;
        this.owner = owner;
        this.seat = seat;
        this.projection = projection;
        this.facility = facility;
        this.fastReservation = false;
        this.version = version;
    }

    public Ticket(SeatStatus seatStatus, boolean fastReservation, boolean taken, RegisteredUser owner, Seat seat,
                  Projection projection, Facility facility, Long version) {
        this.discount = 0;
        this.seatStatus = seatStatus;
        this.taken = taken;
        this.owner = owner;
        this.seat = seat;
        this.projection = projection;
        this.facility = facility;
        this.fastReservation = fastReservation;
        this.version = version;
    }

    public Ticket(SeatStatus seatStatus, boolean fastReservation, boolean taken, RegisteredUser owner, Seat seat,
                  Projection projection, Facility facility, Long version, int discount) {
        this.discount = discount;
        this.seatStatus = seatStatus;
        this.taken = taken;
        this.owner = owner;
        this.seat = seat;
        this.projection = projection;
        this.facility = facility;
        this.fastReservation = fastReservation;
        this.version = version;
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

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public RegisteredUser getOwner() {
        return owner;
    }

    public void setOwner(RegisteredUser owner) {
        this.owner = owner;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public boolean isFastReservation() {
        return fastReservation;
    }

    public void setFastReservation(boolean fastReservation) {
        this.fastReservation = fastReservation;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", seatStatus=" + seatStatus +
                ", taken=" + taken +
                ", owner=" + owner +
                ", seat=" + seat +
                ", projection=" + projection +
                ", facility=" + facility +
                '}';
    }
}
