package com.chattymatty.chattymatty.controllers;

import com.chattymatty.chattymatty.dto.ChannelDto;
import com.chattymatty.chattymatty.entities.Channel;
import com.chattymatty.chattymatty.http.AppResponse;
import com.chattymatty.chattymatty.services.ChannelService;
import com.chattymatty.chattymatty.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> createChannel(@RequestBody ChannelDto channel) {
        Channel createdChannel = channelService.createChannel(channel.getName(), channel.getOwnerId());

        if(createdChannel != null) {
            return AppResponse.success()
                    .withMessage("Channel successfully created")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Channel can not be created")
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getChannelsForUser(@RequestParam int userId) {
        var channelsCollection = channelService.getChannelsForUser(userId);

        if(!channelsCollection.isEmpty()) {
            return AppResponse.success()
                    .withData(channelsCollection)
                    .build();
        }

        return AppResponse.error()
                .withMessage("Empty list")
                .build();
    }

    @PostMapping("/{channelId}/users/{userId}")
    public ResponseEntity<?> addUserToChannel(@PathVariable int channelId, @PathVariable int userId) {
        var response = channelService.addUserToChannel(channelId, userId);

        if(response) {
            return AppResponse.success()
                    .withMessage("User added to the channel successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("User can not be added.")
                .build();

    }

    @PatchMapping("/{channelId}/name")
    public ResponseEntity<?> renameChannel(@PathVariable int channelId, @RequestParam String newName, @RequestParam int ownerId) {
        var response = channelService.renameChannel(channelId, newName, ownerId);
        if(response) {
            return AppResponse.success()
                    .withMessage("Channel successfully renamed")
                    .build();
        }
        return AppResponse.error()
                .withMessage("Only OWNER and ADMIN can rename the channel")
                .build();

    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> deleteChannel(@PathVariable int channelId, @RequestParam int ownerId) {
        var response = channelService.deleteChannel(channelId, ownerId);

        if(response) {
            return AppResponse.success()
                    .withMessage("Cnahhel deleted successfuly")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Only the OWNER can delete the channel")
                .build();
    }
}
