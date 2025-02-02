package com.chattymatty.chattymatty.services;

import com.chattymatty.chattymatty.entities.Channel;
import com.chattymatty.chattymatty.entities.Message;
import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepo;
    private UserService userService;
    private ChannelService channelService;

    public MessageService(MessageRepository messageRepo, UserService userService, ChannelService channelService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
        this.channelService = channelService;
    }

    public boolean sendDirectMessage(int senderId, int receiverId, String content) {
        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);

        if(sender != null && receiver != null) {
            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setContent(content);

            this.messageRepo.save(message);
            return true;
        }
        return false;
    }

    public List<Message> getDirectMessagesBetweenUsers(int userId1, int userId2) {
        User user1 = userService.getUserById(userId1);
        User user2 = userService.getUserById(userId2);

        return this.messageRepo.findAllBySenderOrReceiver(user1, user2).stream()
                .filter(message -> (message.getSender().equals(user1) && message.getReceiver().equals(user2)) ||
                        (message.getSender().equals(user2) && message.getReceiver().equals(user1)))
                .toList();
    }

    public boolean sendChannelMessage(int senderId, int channelId, String content) {
        User sender = userService.getUserById(senderId);
        Channel channel = channelService.getChannelById(channelId);

        if(channel.getUsers().contains(sender)) {
            Message message = new Message();
            message.setSender(sender);
            message.setChannel(channel);
            message.setContent(content);
            this.messageRepo.save(message);
            return true;
        }

        return false;
    }

    public List<Message> getMessagesForChannel(int channelId) {
        Channel channel = channelService.getChannelById(channelId);
        return this.messageRepo.findAllByChannel(channel);
    }
}
