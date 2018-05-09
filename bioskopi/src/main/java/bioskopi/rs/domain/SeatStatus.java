package bioskopi.rs.domain;


import java.io.Serializable;

/**
 * Type of seat
 */
public enum SeatStatus implements Serializable {

    FREE,
    TAKEN,
    CLOSED
}
