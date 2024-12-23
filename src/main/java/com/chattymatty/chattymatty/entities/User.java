package com.chattymatty.chattymatty.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "td_users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "full_name")
    private String fullName;
}
