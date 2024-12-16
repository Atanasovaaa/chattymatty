package com.chattymatty.chattymatty.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "td_friends")
@Getter
@Setter
@NoArgsConstructor
public class UserFriend {

    @Id
    @Column(name = "user_id")
    private int userId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
