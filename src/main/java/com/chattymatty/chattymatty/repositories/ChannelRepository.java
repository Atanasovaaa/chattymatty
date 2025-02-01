package com.chattymatty.chattymatty.repositories;

import com.chattymatty.chattymatty.entities.Channel;
import com.chattymatty.chattymatty.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    List<Channel> findAllByUsersContainingAndIsActiveIsGreaterThan(User user, int isActive);

    List<Channel> findAllByOwnerAndIsActiveIsGreaterThan(User owner, int isActiveIsGreaterThan);
}
