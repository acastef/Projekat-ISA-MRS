package bioskopi.rs.validators;

import bioskopi.rs.domain.UserCategory;

public class UserCategoryValidator {

    public static boolean checkPoints(Iterable<UserCategory> userCategories) {
        for (UserCategory category :
                userCategories) {
            if (category.getPoints() < 0L) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDiscoints(Iterable<UserCategory> userCategories) {
        for (UserCategory category :
                userCategories) {
            if ((category.getDiscount().doubleValue() > 100) || (category.getDiscount().doubleValue() < 0)) {
                return false;
            }
        }
        return true;
    }

}
