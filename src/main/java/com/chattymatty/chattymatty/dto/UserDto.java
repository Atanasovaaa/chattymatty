package com.chattymatty.chattymatty.dto;

import com.chattymatty.chattymatty.entities.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
    private int id;
    private String username;
    private int isActive;

    public UserDto(int id, String username, int isActive) {
        this.id = id;
        this.username = username;
        this.isActive = isActive;
    }

    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getIsActive()
        );
    }
}
