package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.PropsReservation;

import java.io.Serializable;

public class ReservedPropsDTO implements Serializable {

    private long id;
    private Props props;
    private long registeredUser;
    private long quantity;

    public ReservedPropsDTO() {
    }

    public ReservedPropsDTO(long id, Props props, long registeredUser, long quantity) {
        this.id = id;
        this.props = props;
        this.registeredUser = registeredUser;
        this.quantity = quantity;
    }

    public ReservedPropsDTO(PropsReservation propsReservation){
        this.id = propsReservation.getId();
        this.props = propsReservation.getProps();
        this.registeredUser = propsReservation.getRegisteredUser().getId();
        this.quantity = propsReservation.getQuantity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Props getProps() {
        return props;
    }

    public void setProps(Props props) {
        this.props = props;
    }

    public long getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(long registeredUser) {
        this.registeredUser = registeredUser;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
