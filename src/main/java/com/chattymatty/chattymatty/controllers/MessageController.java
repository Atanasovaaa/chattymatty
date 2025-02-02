package com.chattymatty.chattymatty.controllers;

import com.chattymatty.chattymatty.http.AppResponse;
import com.chattymatty.chattymatty.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/direct")
    public ResponseEntity<?> sendDirectMessage(@RequestParam int senderId, @RequestParam int receiverId, @RequestBody String content) {
        var response = messageService.sendDirectMessage(senderId, receiverId, content);

        if(response) {
            return AppResponse.success()
                    .withMessage("Message sent successfully")
                    .build();
        }
        return AppResponse.error()
                .withMessage("Message can not be send")
                .build();
    }

    @PostMapping("/channel")
    public ResponseEntity<?> sendChannelMessage(@RequestParam int senderId, @RequestParam int channelId, @RequestBody String content) {
        var response = messageService.sendChannelMessage(senderId, channelId, content);

        if(response) {
            return AppResponse.success()
                    .withMessage("Message sent")
                    .build();
        }
        return AppResponse.error()
                .withMessage("Message can not be send")
                .build();
    }

    @GetMapping("/direct")
    public ResponseEntity<?> getDirectMessages(@RequestParam int userId1, @RequestParam int userId2) {
        var messagesCollection = messageService.getDirectMessagesBetweenUsers(userId1, userId2);

        if(!messagesCollection.isEmpty()) {
            return AppResponse.success()
                    .withData(messagesCollection)
                    .build();
        }

        return AppResponse.error()
                .withMessage("No messages")
                .build();
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<?> getChannelMessages(@PathVariable int channelId) {
        var messagesForChannelCollection = messageService.getMessagesForChannel(channelId);

        if (!messagesForChannelCollection.isEmpty()) {
            return AppResponse.success()
                    .withData(messagesForChannelCollection)
                    .build();
        }

        return AppResponse.error()
                .withMessage("No messages")
                .build();
    }
}
