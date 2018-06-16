package bioskopi.rs.constants;

import bioskopi.rs.domain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProjectionsConstants {

    public static  Long DB_PROJ_ID = 3123L;
    public static final String DB_PROJ_NAME = "FAC_FAC1";
    public static final String DB_PROJ_ADR = "FAC_ADDR1";
    public static final int DB_PROJ_PRICE = 350;

    public static ViewingRoom viewingRoom;
    public static Cinema cin2;
    public static Projection p;

    public static final int DB_COUNT = 2;

    public static List<Projection> projList = new ArrayList<>();
    public static Projection projection = new Projection();

    public static HashSet<Seat> seats = new HashSet<Seat>();

    public static Facility DB_FAC = new Facility();
}
