package bioskopi.rs.validators;

import bioskopi.rs.domain.UserCategory;
import bioskopi.rs.domain.util.ValidationException;

import static bioskopi.rs.domain.Privilege.BRONZE;
import static bioskopi.rs.domain.Privilege.GOLD;
import static bioskopi.rs.domain.Privilege.SILVER;

public class UserCategoryValidator {

    public static boolean checkPointsValues(Iterable<UserCategory> userCategories) throws ValidationException {
        for (UserCategory category :
                userCategories) {
            if (category.getPoints() < 0L) {
                throw new ValidationException("Points value has to be positive integer");
            }
        }
        return true;
    }

    public static boolean checkDiscountsValues(Iterable<UserCategory> userCategories) throws ValidationException {
        for (UserCategory category :
                userCategories) {
            if ((category.getDiscount().doubleValue() > 100) || (category.getDiscount().doubleValue() < 0)) {
                throw  new ValidationException(
                        "Discount value have to be decimal number with to digits in range of 0 - 100");
            }
        }
        return true;
    }

    public static boolean checkTypes(Iterable<UserCategory> userCategories) throws ValidationException{

        UserCategory gold = new UserCategory();
        UserCategory silver = new UserCategory();
        UserCategory bronze = new UserCategory();
        int count = 0;
        for (UserCategory category :
                userCategories) {
            if(category.getName() == GOLD){
                gold = category;
                ++count;
            }
            else if(category.getName() == SILVER){
                silver = category;
                ++count;
            }
            else if(category.getName() == BRONZE){
                bronze = category;
                ++count;
            }
        }
        if(count != 3){
            throw  new ValidationException("Wrong type of user category. Acceptable types are: GOLD, SILVER, BRONZE");
        }
        else if((gold.getPoints() < silver.getPoints()) || (gold.getPoints() < bronze.getPoints())){
            throw  new ValidationException("Gold points can not be lower than silver or bronze");
        }
        else if((silver.getPoints() > gold.getPoints()) || (silver.getPoints() < bronze.getPoints())){
            throw  new ValidationException("Silver points can not be smaller than bronze or greater than gold");
        }
        else if((bronze.getPoints() > silver.getPoints()) || (bronze.getPoints()> gold.getPoints())){
            throw  new ValidationException("Bronze points can not be greater than silver or gold");
        }
        else if((gold.getDiscount().compareTo(silver.getDiscount()) < 0  ) ||
                (gold.getDiscount().compareTo(bronze.getDiscount()) < 0 )){
            throw  new ValidationException("Gold discount can not be lower than silver or bronze");
        }
        else if((silver.getDiscount().compareTo(gold.getDiscount()) > 0  ) ||
                (silver.getDiscount().compareTo(bronze.getDiscount()) < 0 )){
            throw  new ValidationException("Silver discount can not be smaller than bronze or greater than gold");
        }
        else if((bronze.getDiscount().compareTo(silver.getDiscount()) > 0  ) ||
                (bronze.getDiscount().compareTo(gold.getDiscount()) > 0 )){
            throw  new ValidationException("Bronze discount can not be greater than silver or gold");
        }

        return true;
    }

}
