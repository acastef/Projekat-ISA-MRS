package bioskopi.rs.constants;

import java.io.File;
import java.nio.file.Paths;

public class FacilityConstants {
    private static final String IMAGE_PATH = Paths.get("img","props").toString()
            + File.separator;

    public static final Long DB_ID = 1L;
    public static final Long DB_UNKNOWN_ID = 999L;
    public static final String DB_DESCRIPTION = "hat";
    public static final String DB_IMG = IMAGE_PATH +"img1";
    public static final String DB_LOC = "Cineplex";
    public static final Long DB_QUAN = 5L;

    public static final String DB_IMG1 = "img1";
    public static final String DB_IMG2 = "img2";
    public static final String DB_IMG3 = "img3";

    public static final int DB_COUNT = 3;

    public static boolean DB_INIT = false;
}
