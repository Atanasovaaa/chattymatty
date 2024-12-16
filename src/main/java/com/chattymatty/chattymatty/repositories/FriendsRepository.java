package com.chattymatty.chattymatty.repositories;

import com.chattymatty.chattymatty.entities.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<UserFriend, Integer> {
    UserFriend findUserFriendByUserId(int id);

    List<UserFriend> findAll();
}
