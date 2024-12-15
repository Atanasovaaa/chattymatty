package com.chattymatty.chattymatty.services;

import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        return this.userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }
}
