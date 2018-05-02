package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserServiceImpl implements RegisteredUserService{

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Override
    public RegisteredUser findById(long id) {
        return registeredUserRepository.getOne(id);
    }
}
