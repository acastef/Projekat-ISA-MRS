package bioskopi.rs.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PointsValidator implements ConstraintValidator<PointsConstraints,String> {

    @Override
    public void initialize(PointsConstraints constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value != null) && value.matches("[0-9]+");
    }


}
