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

import java.util.List;

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
    public ResponseEntity<String> addUserToChannel(@PathVariable int channelId, @PathVariable int userId) {
        channelService.addUserToChannel(channelId, userId);
        return ResponseEntity.ok("User added to the channel successfully");
    }

    @PatchMapping("/{channelId}/name")
    public ResponseEntity<String> renameChannel(@PathVariable int channelId, @RequestParam String newName, @RequestParam int ownerId) {
        channelService.renameChannel(channelId, newName, ownerId);
        return ResponseEntity.ok("Channel renamed successfully");
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<String> deleteChannel(@PathVariable int channelId, @RequestParam int ownerId) {
        channelService.deleteChannel(channelId, ownerId);
        return ResponseEntity.ok("Channel deleted successfully");
    }
}
