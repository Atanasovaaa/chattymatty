package com.chattymatty.chattymatty.services;

import com.chattymatty.chattymatty.dto.ChannelDto;
import com.chattymatty.chattymatty.entities.Channel;
import com.chattymatty.chattymatty.entities.User;
import com.chattymatty.chattymatty.repositories.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChannelService {

    private ChannelRepository channelRepo;

    private UserService userService;

    public ChannelService(ChannelRepository channelRepo, UserService userService) {
        this.channelRepo = channelRepo;
        this.userService = userService;
    }

    public Channel createChannel(String channelName, int ownerId) {
        User owner = userService.getUserById(ownerId);
        Channel channel = new Channel();
        channel.setName(channelName);
        channel.setOwner(owner);
        channel.getUsers().add(owner);
        return this.channelRepo.save(channel);
    }

    public void addUserToChannel(int channelId, int userId) {
        Channel channel = getChannelById(channelId);
        User user = userService.getUserById(userId);
        channel.getUsers().add(user);
        this.channelRepo.save(channel);
    }

    public Channel getChannelById(int id) {
        return channelRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Channel not found"));
    }

    public void deleteChannel(int channelId, int ownerId) {
        Channel channel = getChannelById(channelId);
        if (!(channel.getOwner().getId() == ownerId)) {
            throw new IllegalArgumentException("Only owner can delete the channel");
        }
        channel.setIsActive(0);
        this.channelRepo.save(channel);
    }

    public List<ChannelDto> getChannelsForUser(int userId) {
        User user = userService.getUserById(userId);
        List<Channel> channels = channelRepo.findAllByOwnerAndIsActiveIsGreaterThan(user, 0);

        return channels.stream()
                .map(ChannelDto::fromChannel)
                .collect(Collectors.toList());
    }

    public void renameChannel(int channelId, String newName, int userId) {
        Channel channel = getChannelById(channelId);
        User user = userService.getUserById(userId);

        if (channel.getOwner().equals(user) || channel.getAdministrators().contains(user)) {
            channel.setName(newName);
            this.channelRepo.save(channel);
        } else {
            throw new IllegalArgumentException("Only OWNER or ADMIN can rename the channel");
        }
    }

}
