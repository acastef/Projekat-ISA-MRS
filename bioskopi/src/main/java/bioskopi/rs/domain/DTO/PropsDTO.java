package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.Props;

import java.io.Serializable;

public class PropsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String description;

    private String image;

    private String location;

    public PropsDTO() {
    }

    public PropsDTO(long id, String description, String image, String location) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.location = location;
    }

    public PropsDTO(Props props){
        this.id = props.getId();
        this.description = props.getDescription();
        this.image = props.getImage();
        this.location = props.getFacility().getName() + "\n" + props.getFacility().getAddress();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
