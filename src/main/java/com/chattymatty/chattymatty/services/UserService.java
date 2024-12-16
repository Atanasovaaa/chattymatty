package com.chattymatty.chattymatty.services;

import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.entities.UserFriend;
import com.chattymatty.chattymatty.repositories.FriendsRepository;
import com.chattymatty.chattymatty.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;
    private FriendsRepository friendsRepo;

    public UserService(UserRepository userRepo, FriendsRepository friendsRepo) {
        this.userRepo = userRepo;
        this.friendsRepo = friendsRepo;
    }

    public User createUser(User user) {
        return this.userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    public boolean addFriend(User user) {
        UserFriend userFriend = this.friendsRepo.findUserFriendByUserId(user.getId());

        if(userFriend != null) {
            this.friendsRepo.save(userFriend);
            return true;
        }

        UserFriend newUserFriend = new UserFriend();
        newUserFriend.setUserId(user.getId());
        this.friendsRepo.save(newUserFriend);
        return true;
    }
}
