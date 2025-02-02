package com.chattymatty.chattymatty.services;

import com.chattymatty.chattymatty.dto.UserDto;
import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        this.userRepo.findByUsernameAndIsActiveIsGreaterThan(user.getUsername(), 0).ifPresent(
            existinUser -> {
                throw new IllegalArgumentException("Username already exists");
            }
        );
        user.getRoles().add("GUEST");
        return this.userRepo.save(user);
    }

    public List<User> searchUsers(String keyword) {
        return this.userRepo.findByUsernameContainingAndIsActiveIsGreaterThan(keyword, 0);
    }

    public List<UserDto> getAllUsers(int userId) {
        User user = getUserById(userId);
        Set<Integer> friendIds = user.getFriends()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        return userRepo.findAll().stream()
                .filter(u -> !friendIds.contains(u.getId()))
                .filter(u -> u.getId() != userId)
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }
//    public List<User> getAllUsers() {
//        return this.userRepo.findAllByIsActiveIsGreaterThan(0);
//    }

    public boolean addFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        if(user.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Friend already added");
        }

        user.getFriends().add(friend);
        this.userRepo.save(user);
        return true;
    }

    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<UserDto> getFriends(int userId) {
        User user = getUserById(userId);
        Set<User> friends = user.getFriends();
        return friends.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public boolean deleteUser(int id) {
        User user = getUserById(id);

        if(user != null) {
            user.setIsActive(0);
            this.userRepo.save(user);
            return true;
        }

        return false;
    }
}
