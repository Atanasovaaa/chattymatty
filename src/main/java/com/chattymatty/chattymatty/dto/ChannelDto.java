package com.chattymatty.chattymatty.dto;

import com.chattymatty.chattymatty.entities.Channel;
import com.chattymatty.chattymatty.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ChannelDto {
    private int id;
    private String name;
    private int ownerId;

    public ChannelDto(int id, String name, int ownerId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
    }

    public static ChannelDto fromChannel(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getName(),
                channel.getOwner().getId()
        );
    }
}
