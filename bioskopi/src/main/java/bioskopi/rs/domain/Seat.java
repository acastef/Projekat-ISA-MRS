package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seat")
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String seatRow;

    @Column(nullable = false)
    private String seatColumn;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SegmentEnum segment;


    @ManyToOne(optional = false)
    private ViewingRoom viewingRoom;

    public Seat() {
    }

    public Seat(long id, String seatRow, String column , SegmentEnum segment, ViewingRoom viewingRoom) {
        this.id = id;
        this.seatRow = seatRow;
        this.seatColumn = column;
        this.segment = segment;
        this.viewingRoom = viewingRoom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public String getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(String column) {
        this.seatColumn = column;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

        public SegmentEnum getSegment() {
        return segment;
    }

    public void setSegment(SegmentEnum segment) {
        this.segment = segment;
    }

    public ViewingRoom getViewingRoom() {
        return viewingRoom;
    }

    public void setViewingRoom(ViewingRoom viewingRoom) {
        this.viewingRoom = viewingRoom;
    }
}
