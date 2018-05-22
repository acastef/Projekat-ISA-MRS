package bioskopi.rs.domain;

import bioskopi.rs.domain.util.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private double offer;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private RegisteredUser user;

    @JsonBackReference
    @ManyToOne(optional = false)
    private Ad ad;

    public Bid() {
    }

    public Bid(long id, double offer, LocalDateTime date, RegisteredUser user, Ad ad) {
        this.id = id;
        this.offer = offer;
        this.date = date;
        this.user = user;
        this.ad = ad;
    }

    public Bid( double offer, LocalDateTime date, RegisteredUser user, Ad ad) {
        this.offer = offer;
        this.date = date;
        this.user = user;
        this.ad = ad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
