package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.SegmentEnum;

import javax.persistence.*;
import java.io.Serializable;

public class SeatDTO implements Serializable {

    private static long serialVersionUID = 1L;
    private long id;

    private String seatRow;

    private String seatColumn;

    private SegmentEnum segment;

    public SeatDTO() {
    }

    public SeatDTO(long id, String seatRow, String column , SegmentEnum segment) {
        this.id = id;
        this.seatRow = seatRow;
        this.seatColumn = column;
        this.segment = segment;
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
}
