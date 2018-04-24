package bioskopi.rs.domain;
//
////import com.sun.javafx.beans.IDProperty;
//
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
    private String row;

    @Column(nullable = false)
    private String col;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SegmentEnum segment;

//    @Column(nullable = false)
    @ManyToOne(optional = false)
    private ViewingRoom viewingRoom;

    public Seat() {
    }

    public Seat(long id, String row, String column , SegmentEnum segment, ViewingRoom viewingRoom) {
        this.id = id;
        this.row = row;
        this.col = column;
        this.segment = segment;
        this.viewingRoom = viewingRoom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String column) {
        this.col = column;
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
