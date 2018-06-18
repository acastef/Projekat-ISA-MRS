package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.AuthorityEnum;
import bioskopi.rs.domain.RegisteredUser;

public class RegisteredUserDTO extends UserDTO {

    private String password;

    private AuthorityEnum authorities;

    private boolean firstLogin;

    public RegisteredUserDTO() {

    }

    public RegisteredUserDTO(long id, String name, String surname, String email, String username, String avatar,
                             String telephone, String address, String password, boolean firstLogin) {
        super(id, name, surname, email, username, avatar, telephone, address);
        this.password = password;
        this.firstLogin = firstLogin;
    }

    public RegisteredUserDTO(RegisteredUser user, String password){
        super(user.getId(),user.getName(),user.getSurname(),user.getEmail(),user.getUsername(),user.getAvatar(),
                user.getTelephone(),user.getAddress());
        this.password = password;
        this.authorities = user.getAuthorities();
        this.firstLogin = user.isFirstLogin();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthorityEnum getAuthorities() {
        return authorities;
    }

    public void setAuthorities(AuthorityEnum authorities) {
        this.authorities = authorities;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogIn) {
        this.firstLogin = firstLogin;
    }
}
