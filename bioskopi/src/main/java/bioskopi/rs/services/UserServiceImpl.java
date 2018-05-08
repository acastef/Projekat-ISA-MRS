package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RegisteredUser> findAllUsers(){ return userRepository.findAll();};

    @Override
    public RegisteredUser findByUsername(String username) {return userRepository.findByUsername(username);}

    @Override
    public RegisteredUser add(RegisteredUser registeredUser) throws ValidationException {
        List<RegisteredUser> regUsers = userRepository.findAll();
        for(RegisteredUser ru : regUsers){
            if (ru.getUsername().equals(registeredUser.getUsername())){
                throw new ValidationException("Username duplicated!");
            }
        }
        return userRepository.saveAndFlush(registeredUser);
    }
}
