package bioskopi.rs.constants;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.UserCategory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static bioskopi.rs.domain.Privilege.BRONZE;
import static bioskopi.rs.domain.Privilege.GOLD;
import static bioskopi.rs.domain.Privilege.SILVER;

public class PointsScaleConstants {

    public static long DB_FAC_ID = 0;
    public static long DB_PS_ID = 0;

    public static Facility DB_FC;

    public static final String DB_FAC_NAME = "PS1";
    public static final String DB_TYPE = "GOLD";

    public static final Long DB_PNT_G = 80L;
    public static final Long DB_PNT_S = 60L;
    public static final Long DB_PNT_B = 40L;

    public static final BigDecimal DB_DIS_G = new BigDecimal("86.27");
    public static final BigDecimal DB_DIS_S = new BigDecimal("67.41");
    public static final BigDecimal DB_DIS_B = new BigDecimal("52.36");

    public static int DB_COUNT = 2;
    public static int DB_UC_COUNT = 3;

    public static final long NEW_PNT_G = 60L;
    public static final BigDecimal NEW_DIS_G = new BigDecimal("60.56");

    public static boolean DB_INIT = false;
}
