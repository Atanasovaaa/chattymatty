package com.chattymatty.chattymatty.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "td_messages")
@Getter
@Setter
@NoArgsConstructor
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();
}
