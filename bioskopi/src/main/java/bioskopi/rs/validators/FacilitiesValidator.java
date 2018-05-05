package bioskopi.rs.validators;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.util.ValidationException;
import sun.invoke.util.VerifyAccess;

public class FacilitiesValidator {

    public static void checkFacility(Facility facility) throws ValidationException{
        checkViewingRooms(facility);
        checkPointScales(facility);
    }

    private static void checkViewingRooms(Facility facility) throws ValidationException{
        if((facility.getViewingRooms() == null) || (facility.getViewingRooms().size() < 1)){
            throw new ValidationException("Facility has to contains at least one viewing room");
        }
    }

    private static void checkPointScales(Facility facility) throws ValidationException{
        if((facility.getPointsScales() == null) || (facility.getPointsScales().getUserCategories() == null)
                || (facility.getPointsScales().getUserCategories().isEmpty())){
            throw new ValidationException("User categories are not specified");
        }
        else if(((facility.getPointsScales() == null)) || (facility.getPointsScales().getUserCategories() == null)
                || (facility.getPointsScales().getUserCategories().size() < 3)){
            throw  new ValidationException("Requires tree user categories: BRONZE, SILVER, GOLD");
        }
    }
}
