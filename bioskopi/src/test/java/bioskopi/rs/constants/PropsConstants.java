package bioskopi.rs.constants;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;

public class PropsConstants {
    private static final String IMAGE_PATH = Paths.get("img","props").toString()
            + File.separator;

    public static final Long DB_ID = 1L;
    public static final String DB_DESCRIPTION = "hat";
    public static final String DB_IMG = IMAGE_PATH +"img1";
    public static final String DB_LOC = "loc1";
    public static final Long DB_QUAN = 5L;


    public static final int DB_COUNT = 3;
}
