package bioskopi.rs.constants;

import bioskopi.rs.domain.Ad;
import bioskopi.rs.domain.AdState;
import bioskopi.rs.domain.RegisteredUser;

public class AdConstants {

    public static RegisteredUser RUSER1 = new RegisteredUser();
    public static RegisteredUser RUSER2 = new RegisteredUser();
    public static Ad AD = new Ad();

    public static int DB_COUNT1 = 1;
    public static String DB_IMG1 = "ad1";
    public static String DB_NM1 = "ad1";
    public static String DB_DS1 = "ad1";
    public static java.time.LocalDateTime DB_DL1 = java.time.LocalDateTime.now();
    public static AdState DB_ST1 = AdState.WAIT;
    public static Long DB_VR1 = 0L;

    public static int DB_COUNT2 = 1;
    public static String DB_IMG2 = "ad2";
    public static String DB_NM2 = "ad2";
    public static String DB_DS2 = "ad2";
    public static java.time.LocalDateTime DB_DL2 = java.time.LocalDateTime.now();
    public static AdState DB_ST2 = AdState.ACTIVE;
    public static Long DB_VR2 = 0L;

    public static String NEW_IMG = "ad3";
    public static String NEW_NM = "ad3";
    public static String NEW_DS = "ad3";
    public static java.time.LocalDateTime NEW_DL = java.time.LocalDateTime.now();
    public static AdState NEW_ST = AdState.ACTIVE;
    public static Long NEW_VR = 0L;

}
