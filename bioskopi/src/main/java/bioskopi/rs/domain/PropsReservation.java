package bioskopi.rs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class PropsReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Props props;

    @JsonBackReference
    @ManyToOne(optional = false)
    private RegisteredUser registeredUser;

    @Column(nullable = false)
    @Min(value = 1, message = "Minimum quantity is 1")
    private long quantity;

    public PropsReservation() {
        this.quantity = 1;
    }

    public PropsReservation(long id, Props props, RegisteredUser registeredUser, long quantity) {
        this.id = id;
        this.props = props;
        this.registeredUser = registeredUser;
        this.quantity = quantity;
    }

    public PropsReservation( Props props, RegisteredUser registeredUser, long quantity) {
        this.props = props;
        this.registeredUser = registeredUser;
        this.quantity = quantity;
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

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
