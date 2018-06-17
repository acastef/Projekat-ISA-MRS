package bioskopi.rs.constants;

import bioskopi.rs.domain.Cinema;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Seat;

public class FacilitiesConstants {

    public static  Long DB_FAC_ID = 0L;
    public static final String DB_FAC_NAME = "FAC_FAC1";
    public static final String DB_FAC_ADR = "FAC_ADDR1";

    public static final int DB_COUNT = 2;

    public static final String NEW_FAC_NAME = "TEMP";
    public static final String NEW_FAC_ADR = "TEMP_ADR";
    public static final String NEW_VM_NAME = "VM_TEMP";

    public static final String NEW_THA_NAME = "THA_TEMP";

    public static boolean DB_INIT = false;

    public static Facility DB_FAC_CIN = new Facility();
    public static Seat DB_SEAT = new Seat();
}
