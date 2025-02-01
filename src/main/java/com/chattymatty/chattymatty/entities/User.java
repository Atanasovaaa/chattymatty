package com.chattymatty.chattymatty.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "td_users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "td_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tc_user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();
}
