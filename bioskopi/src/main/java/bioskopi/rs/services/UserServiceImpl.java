package bioskopi.rs.services;

import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RegisteredUser> findAllUsers(){ return userRepository.findAll();};

    @Override
    public RegisteredUser findByUsername(String username) {return userRepository.findByUsername(username);}
}
