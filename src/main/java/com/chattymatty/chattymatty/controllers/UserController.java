package com.chattymatty.chattymatty.controllers;

import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.http.AppResponse;
import com.chattymatty.chattymatty.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        var userCollection = this.userService.getAllUsers();

        if(!userCollection.isEmpty()) {
            return AppResponse.success()
                    .withData(userCollection)
                    .build();
        }

        return AppResponse.error()
                .withMessage("Empty data for users")
                .build();
    }

    @PostMapping("/users")
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

    @PostMapping("/users/add-friend")
    public ResponseEntity<?> addFriend(@RequestBody User user) {
        if(this.userService.addFriend(user)) {
            return AppResponse.success()
                    .withMessage("New friend added")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Something went wrong")
                .build();
    }
}
