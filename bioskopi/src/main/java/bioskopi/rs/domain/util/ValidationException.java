package bioskopi.rs.domain.util;

public class ValidationException extends RuntimeException {


    public ValidationException() {
    }

    public ValidationException(String message) {

        super(message);
    }

}
