package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.Friendship;
import bioskopi.rs.domain.RegisteredUser;
import bioskopi.rs.domain.User;
import bioskopi.rs.repository.FriendshipRepository;
import bioskopi.rs.repository.RegisteredUserRepository;
import bioskopi.rs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service("friendshipService")
public class FriendshipServiceImpl implements FriendshipService {


    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Override
    public List<BigInteger> getFriends(long id) {
        List<BigInteger> ids = friendshipRepository.getAllFriends(id);
        return ids;
    }

    @Override
    public List<UserDTO> getAll(long id) {
        List<BigInteger> ids = friendshipRepository.getAllFriends(id);
        List<Long> idAll = new ArrayList<Long>();
        for(BigInteger b : ids){
            idAll.add(b.longValue());
        }
        List<User> users = userRepository.findAllById(idAll);
        List<UserDTO> allUsers = new ArrayList<>();
        for(User u : users) {
            allUsers.add(new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getUsername(),
                    u.getAvatar(), u.getTelephone(), u.getAddress()));
        }
        return allUsers;
    }

    @Override
    public List<UserDTO> getAllNonFriends(long id) {
        List<BigInteger> ids = friendshipRepository.getAllNonFriends(id);
        List<Long> idAll = new ArrayList<Long>();
        for(BigInteger b : ids){
            idAll.add(b.longValue());
        }
        List<User> users = userRepository.findAllById(idAll);
        List<UserDTO> allUsers = new ArrayList<>();
        for(User u : users) {
            allUsers.add(new UserDTO(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getUsername(),
                    u.getAvatar(), u.getTelephone(), u.getAddress()));
        }
        return allUsers;
    }

    @Override
    public void addFriendship(UserDTO first, UserDTO second) {
        RegisteredUser firstReg = registeredUserRepository.getByUserName(first.getUsername());
        RegisteredUser secondReg = registeredUserRepository.getByUserName(second.getUsername());
        Friendship newFriends = new Friendship(firstReg, secondReg);
        Friendship newFriends2 = new Friendship(secondReg, firstReg);
        friendshipRepository.saveAndFlush(newFriends);
        friendshipRepository.saveAndFlush(newFriends2);
    }

    @Override
    public void deleteFriendship(UserDTO first, UserDTO second) {
        Friendship newFriends = friendshipRepository.getFriendshipByUsers(first.getId(), second.getId());
        Friendship newFriends2 = friendshipRepository.getFriendshipByUsers(second.getId(), first.getId());
        friendshipRepository.delete(newFriends);
        friendshipRepository.delete(newFriends2);
    }


}
