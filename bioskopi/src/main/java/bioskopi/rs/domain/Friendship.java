package bioskopi.rs.domain;

import bioskopi.rs.domain.util.FriendshipSerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name="Friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable=false)
    private long id;

    @ManyToOne(optional = false)
    @JsonSerialize(using = FriendshipSerializer.class)
    private RegisteredUser first;

    @JsonSerialize(using = FriendshipSerializer.class)
    @ManyToOne(optional = false)
    private RegisteredUser second;

    @Column(nullable = false)
    private FriendshipStatus status;

    public Friendship(){

    }

    public Friendship(long id, RegisteredUser first, RegisteredUser second, FriendshipStatus status) {
        this.id = id;
        this.first = first;
        this.second = second;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RegisteredUser getFirst() {
        return first;
    }

    public void setFirst(RegisteredUser first) {
        this.first = first;
    }

    public RegisteredUser getSecond() {
        return second;
    }

    public void setSecond(RegisteredUser second) {
        this.second = second;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
