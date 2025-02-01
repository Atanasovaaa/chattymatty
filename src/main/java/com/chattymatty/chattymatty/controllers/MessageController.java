package com.chattymatty.chattymatty.controllers;


import com.chattymatty.chattymatty.entities.Message;
import com.chattymatty.chattymatty.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/direct")
    public ResponseEntity<String> sendDirectMessage(@RequestParam int senderId, @RequestParam int receiverId, @RequestBody String content) {
        messageService.sendDirectMessage(senderId, receiverId, content);
        return ResponseEntity.ok("Message sent successfully");
    }

    @PostMapping("/channel")
    public ResponseEntity<String> sendChannelMessage(@RequestParam int senderId, @RequestParam int channelId, @RequestBody String content) {
        messageService.sendChannelMessage(senderId, channelId, content);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/direct")
    public ResponseEntity<List<Message>> getDirectMessages(@RequestParam int userId1, @RequestParam int userId2) {
        return ResponseEntity.ok(messageService.getDirectMessagesBetweenUsers(userId1, userId2));
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<Message>> getChannelMessages(@PathVariable int channelId) {
        return ResponseEntity.ok(messageService.getMessagesForChannel(channelId));
    }
}
