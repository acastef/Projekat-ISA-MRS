package bioskopi.rs.constants;

import bioskopi.rs.domain.Privilege;

import java.math.BigDecimal;

public class UserCategoryConstants {
    public static final Long DB_ID = 1L;
    public static final Privilege DB_NAME = Privilege.GOLD;
    public static final BigDecimal DB_DSC = new BigDecimal("0.0");
    public static final Long DB_PTS = 80L;

    public static final int DB_COUNT = 6;
    public static boolean DB_INIT = false;
}
