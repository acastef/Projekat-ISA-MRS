package bioskopi.rs.validators;

import bioskopi.rs.domain.User;

import java.util.List;

public class AuthorityValidator {

    public static boolean checkAuthorities(User user, List<String> authorities){
        try {
            return authorities.contains(user.getAuthorities());
        }catch (Exception e){
            return false;
        }
    }
}
