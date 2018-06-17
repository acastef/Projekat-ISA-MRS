package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.RegisteredUserRepository;
import bioskopi.rs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Override
    public List<RegisteredUser> findAllUsers(){
        List<User> users = userRepository.findAll();
        List<RegisteredUser> regUsers = new ArrayList<>();
        for(User u : users){
            regUsers.add(new RegisteredUser(u.getId(), u.getName(), u.getSurname(), u.getEmail(),
                            u.getUsername(), u.getPassword(), u.getAvatar(), u.isFirstLogin(), u.getTelephone(),
                            u.getAddress()));
        }
        return regUsers;
    }

    @Override
    public User findByUsername(String username) {return userRepository.findByUsername(username);}

    @Override
    public RegisteredUser getByUsername(String username) {return registeredUserRepository.getByUserName(username);}

    @Override
    public RegisteredUser add(RegisteredUser registeredUser) throws ValidationException {
        List<User> regUsers = userRepository.findAll();
        for(User ru : regUsers){
            if (ru.getUsername().equals(registeredUser.getUsername())){
                throw new ValidationException("Username duplicated!");
            }
        }
        return userRepository.saveAndFlush(registeredUser);
    }

    @Override
    public void activateUser(String username) {
        RegisteredUser toChange = registeredUserRepository.getByUserName(username);
        RegisteredUser changed = toChange;
        if(!toChange.isFirstLogin()) {
            changed.setFirstLogin(true);
            userRepository.delete(toChange);
            userRepository.save(changed);
        }
    }

    @Override
    public User save(RegisteredUser registeredUser) {
        return userRepository.save(registeredUser);
    }
}
