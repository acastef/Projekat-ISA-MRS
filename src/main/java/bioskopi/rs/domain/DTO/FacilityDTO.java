package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.Facility;

import java.io.Serializable;

public class FacilityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String address;

    private String description;

    private String type;

    public FacilityDTO() {
    }

    public FacilityDTO(long id, String name, String address, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.type = "";
    }

    public FacilityDTO(Facility f) {
        this.id = f.getId();
        this.name = f.getName();
        this.address = f.getAddress();
        this.description = f.getDescription();
        this.type = "";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
