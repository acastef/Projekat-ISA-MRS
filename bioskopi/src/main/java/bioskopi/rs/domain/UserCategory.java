package bioskopi.rs.domain;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * Represents user category
 */
@Entity
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private long points;

    @Column(nullable = false)
    @DecimalMin("0")
    @DecimalMax("100")
    private BigDecimal discount;

    @ManyToOne(optional = false)
    private PointsScale pointsScale;

    public UserCategory() {
        this.discount = BigDecimal.ZERO;
    }

    public UserCategory(long id, String name, long points, BigDecimal discount, PointsScale pointsScale) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.discount = discount;
        this.pointsScale = pointsScale;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public PointsScale getPointsScale() {
        return pointsScale;
    }

    public void setPointsScale(PointsScale pointsScale) {
        this.pointsScale = pointsScale;
    }

    @Override
    public String toString() {
        return "UserCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", discount=" + discount +
                '}';
    }
}
