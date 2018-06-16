package bioskopi.rs.domain.util;

public enum ImageFormats {
    JPG("JPG"),
    PNG("PNG"),
    GIF("GIF"),
    TIFF("TIFF"),
    BMP("BMP");

    private final String name;

    ImageFormats(String name){
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
