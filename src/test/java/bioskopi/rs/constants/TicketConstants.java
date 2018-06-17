package bioskopi.rs.constants;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.Seat;

public class TicketConstants {

    public static long DB_TIC_ID = 1L;
    public static long DB_TIC_ID2 = 2L;
    public static long DB_PROJ_ID;
    public static long DB_FAC_ID;
    public static long DB_USER_ID;
    public static boolean DB_INIT = false;

    public static RegisteredUser user = new RegisteredUser();
    public static Projection p = new Projection();
    public static Seat seat = new Seat();
    public static Facility cinema = new Facility();
}
