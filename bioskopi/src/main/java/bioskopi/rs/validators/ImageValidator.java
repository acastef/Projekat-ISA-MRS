package bioskopi.rs.validators;

import bioskopi.rs.domain.util.UploadResponse;
import bioskopi.rs.domain.util.ValidationException;
import org.springframework.data.repository.query.Param;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageValidator {

    public static List<String> imageFormats = new ArrayList<String>() {{
        add("JPG");
        add("PNG");
        add("GIF");
        add("TIFF");
        add("BMP");
    }};

    /**
     * @param imageFile that needs to be checked
     * @return is acceptable image format
     */
    public static boolean checkExtension(String imageFile) {
        String[] tokens = imageFile.split("\\.");
        return imageFormats.contains(tokens[tokens.length - 1].toUpperCase());
    }

    /**
     * @param imageFile that need be processed
     * @return full path of generated name
     * @throws IOException can not create directory
     */
    public static UploadResponse generateName(String imageFile) throws IOException {


        String[] tokens = imageFile.split("\\.");
        String current = new File(".").getCanonicalPath();
        String path = current + File.separator + "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "static" + File.separator + "img" + File.separator + "props";
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdirs();
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return new UploadResponse("img-1-1." + tokens[tokens.length - 1],
                    path + File.separator + "img-1-1." + tokens[tokens.length - 1]);
        } else {
            int count = files.length;
            return new UploadResponse("img-" + ++count + "-1." + tokens[tokens.length - 1],
                    path + File.separator + "img-" + count + "-1." + tokens[tokens.length - 1]);
        }
    }

    public static UploadResponse generateAvatarName(String imageFile) throws IOException{
        String[] tokens = imageFile.split("\\.");
        String current = new File(".").getCanonicalPath();
        String path = current + File.separator + "src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "static" + File.separator + "img" + File.separator + "avatars";
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdirs();
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return new UploadResponse("img-1-2." + tokens[tokens.length - 1],
                    path + File.separator + "img-1-2." + tokens[tokens.length - 1]);
        } else {
            int count = files.length;
            return new UploadResponse("img-" + ++count + "-2." + tokens[tokens.length - 1],
                    path + File.separator + "img-" + count + "-2." + tokens[tokens.length - 1]);
        }
    }
}
