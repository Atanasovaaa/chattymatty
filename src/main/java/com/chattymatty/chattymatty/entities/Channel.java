package com.chattymatty.chattymatty.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "td_channels")
@Getter
@Setter
@NoArgsConstructor
public class Channel extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "tc_channel_users",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tc_channel_admins",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> administrators = new HashSet<>();
}
