package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;

public interface RegisteredUserService {

    RegisteredUser findById(long id);
}
