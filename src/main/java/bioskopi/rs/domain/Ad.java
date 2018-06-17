package bioskopi.rs.domain;

import bioskopi.rs.domain.util.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents ad entity
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String image;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime deadline;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AdState state;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private RegisteredUser owner;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ad")
    private Set<Bid> bids;

    @Version
    @Column(nullable = false)
    private Long version;

    public Ad() {
        version = 0L;
    }

    public Ad(long id, String image, String name, String description, LocalDateTime deadline, AdState state,
              RegisteredUser owner, Set<Bid> bids, Long version) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.state = state;
        this.owner = owner;
        this.bids = bids;
        this.version = version;
    }

    public Ad( String image, String name, String description, LocalDateTime deadline, AdState state,
              RegisteredUser owner, Set<Bid> bids, Long version) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.state = state;
        this.owner = owner;
        this.bids = bids;
        this.version = version;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public AdState getState() {
        return state;
    }

    public void setState(AdState state) {
        this.state = state;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public RegisteredUser getOwner() {
        return owner;
    }

    public void setOwner(RegisteredUser owner) {
        this.owner = owner;
    }
}
