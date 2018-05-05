package bioskopi.rs.domain;
import bioskopi.rs.validators.PointsConstraints;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * Represents user category entity
 */
@Entity
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Privilege name;

    @Column(nullable = false)
    //@PointsConstraints
    private long points;

    @Column(nullable = false)
    @DecimalMin(value = "0",message = "Discount has to be decimal number with to digits in range of 0 - 100")
    @DecimalMax(value = "100",message = "Discount has to be decimal number with to digits in range of 0 - 100")
    private BigDecimal discount;

    @JsonBackReference
    @ManyToOne(optional = false)
    private PointsScale pointsScale;

    public UserCategory() {
        this.discount = BigDecimal.ZERO;
    }

    public UserCategory(long id, Privilege name, long points, BigDecimal discount, PointsScale pointsScale) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.discount = discount;
        this.pointsScale = pointsScale;
    }

    public UserCategory( Privilege name, long points, BigDecimal discount, PointsScale pointsScale) {
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

    public Privilege getName() {
        return name;
    }

    public void setName(Privilege name) {
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
