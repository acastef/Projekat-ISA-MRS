package bioskopi.rs.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PointsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PointsConstraints {
    String message() default "Points have to be positive integer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
