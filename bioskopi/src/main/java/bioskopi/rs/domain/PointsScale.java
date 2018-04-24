package bioskopi.rs.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class PointsScale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pointsScale")
    private Set<UserCategory> userCategories;

    @ManyToOne(optional = false)
    private Facility facility;

    public PointsScale() {
    }


    public PointsScale(long id, Set<UserCategory> userCategories, Facility facility) {
        this.id = id;
        this.userCategories = userCategories;
        this.facility = facility;
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

    public Set<UserCategory> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(Set<UserCategory> userCategories) {
        this.userCategories = userCategories;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
