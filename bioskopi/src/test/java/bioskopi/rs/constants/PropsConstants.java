package bioskopi.rs.constants;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Props;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PropsConstants {
    private static final String IMAGE_PATH = Paths.get("img","props").toString()
            + File.separator;

    public static final Long DB_ID = 1L;
    public static final String DB_DESCRIPTION = "hat";
    public static final String DB_IMG = IMAGE_PATH +"img1";
    public static final String DB_LOC = "Cineplex";
    public static final String DB_PLACE ="Cineplex\naddr1";

    public static final String DB_IMG1 = "img1";
    public static final String DB_IMG2 = "img2";
    public static final String DB_IMG3 = "img3";

    public static final int DB_COUNT = 3;

    public static boolean DB_INIT_P = false;

}
