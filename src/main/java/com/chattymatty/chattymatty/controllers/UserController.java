package com.chattymatty.chattymatty.controllers;

import com.chattymatty.chattymatty.dto.UserDto;
import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.http.AppResponse;
import com.chattymatty.chattymatty.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        var response = this.userService.createUser(user);

        if(response != null) {
            return AppResponse.success()
                    .withData(response)
                    .build();
        }

        return AppResponse.error()
                .withMessage("User can not be added")
                .build();
    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<?> getAllUsers(@PathVariable int userId) {
        var userCollection = this.userService.getAllUsers(userId);

        if(!userCollection.isEmpty()) {
            return AppResponse.success()
                    .withData(userCollection)
                    .build();
        }

        return AppResponse.error()
                .withMessage("Empty list")
                .build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getFriends(@PathVariable int userId) {
        List<UserDto> friends = userService.getFriends(userId);

        return AppResponse.success()
                    .withData(friends)
                    .build();
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable int userId, @PathVariable int friendId) {
        var response = this.userService.addFriend(userId, friendId);

        if(response) {
            return AppResponse.success()
                    .withData("Friend added successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("User can not be added")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        var response = userService.deleteUser(id);

        if(response) {
            return AppResponse.success()
                    .withMessage("User deleted successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("User can not be deleted")
                .build();
    }
}
