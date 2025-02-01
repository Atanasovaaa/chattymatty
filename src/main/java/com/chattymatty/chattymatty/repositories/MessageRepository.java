package com.chattymatty.chattymatty.repositories;

import com.chattymatty.chattymatty.entities.Channel;
import com.chattymatty.chattymatty.entities.Message;
import com.chattymatty.chattymatty.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderOrReceiver(User sender, User receiver);

    List<Message> findAllByChannel(Channel channel);
}
